package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.ApplicationDenied;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.dto.AttachmentDTO;
import ojt.management.common.payload.request.ApplicationCreateRequest;
import ojt.management.common.payload.request.ApplicationUpdateRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.entities.Application;
import ojt.management.data.entities.Attachment;
import ojt.management.data.entities.Job;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.ApplicationRepository;
import ojt.management.data.repositories.AttachmentRepository;
import ojt.management.data.repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final AttachmentRepository attachmentRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  JobRepository jobRepository,
                                  AccountRepository accountRepository, AttachmentRepository attachmentRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.accountRepository = accountRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Page<Application> searchApplication(Specification<Application> specification, Pageable pageable) {
        return applicationRepository.findAll(specification, pageable);
    }

    @Override
    public Application getAppById(Long id, Long accountId)
            throws ApplicationNotExistedException, AccountIdNotExistedException {
        if (Boolean.FALSE.equals(applicationRepository.existsById(id))) {
            throw new ApplicationNotExistedException();
        }
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        }
        Account account = accountRepository.getById(accountId);
        if (account.getRepresentative() != null) {
            return applicationRepository.getAppRep(id, account.getRepresentative().getCompany().getId());
        } else if (account.getStudent() != null) {
            return applicationRepository.getAppStudent(id, account.getStudent().getId());
        } else {
            return applicationRepository.getById(id);
        }
    }

    @Override
    public boolean deleteApplication(Long id, Long accountId)
            throws ApplicationNotExistedException, NotPermissionException {
        if (Boolean.FALSE.equals(applicationRepository.existsById(id))) {
            throw new ApplicationNotExistedException();
        }
        Application application = applicationRepository.getById(id);
        if (!application.isDisabled()) {
            if (application.getStudent().getAccount().getId().equals(accountId)) {
                application.setDisabled(true);
                applicationRepository.save(application);
                return true;
            } else {
                throw new NotPermissionException();
            }
        }
        return false;
    }

    @Override
    public Application updateApplication(Long id, ApplicationUpdateRequest applicationUpdateRequest, Long accountId)
            throws ApplicationNotExistedException, NotPermissionException, ApplicationDenied {
        if (Boolean.FALSE.equals(applicationRepository.existsById(id))) {
            throw new ApplicationNotExistedException();
        }
        Application application = applicationRepository.getById(id);
        if (application.isDisabled()) {
            throw new ApplicationNotExistedException();
        }

        Account account = accountRepository.getById(accountId);
        if (account.isAdmin()) {
            application.setSchoolDenied(applicationUpdateRequest.isSchoolDenied());
        } else if (!account.isAdmin() && !application.isSchoolDenied()) {
            //Company accept application
            //Company id of application == company id of account
            if (application.getJob().getCompany().getId() == account.getRepresentative().getCompany().getId()) {
                if (!application.isStudentConfirmed()) {
                    application.setCompanyAccepted(applicationUpdateRequest.isCompanyAccepted());
                    application.setAcceptedAt(new Timestamp(System.currentTimeMillis()));
                } else {
                    throw new NotPermissionException();
                }
            }
            //student account id of application == student account id of account
            if (application.getStudent().getAccount().getId() == account.getStudent().getAccount().getId()) {
                //Student confirm application
                if (application.isCompanyAccepted()) {
                    application.setStudentConfirmed(applicationUpdateRequest.isStudentConfirmed());
                    application.setConfirmedAt(new Timestamp(System.currentTimeMillis()));
                }
                //Student update exp
                application.setExperience(applicationUpdateRequest.getExperience());
            }
        } else {
            throw new ApplicationDenied();
        }
        return applicationRepository.save(application);
    }

    @Override
    @Transactional
    public Application createApplication(ApplicationCreateRequest applicationCreateRequest, Long accountId) {
        Account account = accountRepository.getById(accountId);
        Job job = jobRepository.getById(applicationCreateRequest.getJobId());
        Application application = new Application();
        application.setExperience(applicationCreateRequest.getExperience());
        application.setJob(job);
        application.setStudent(account.getStudent());
        application.setStudentConfirmed(false);
        application.setCompanyAccepted(false);
        application = applicationRepository.save(application);

        List<String> attachmentKeys = applicationCreateRequest
                .getAttachments().stream().map(AttachmentDTO::getKey).collect(Collectors.toList());
        if(!attachmentKeys.isEmpty() && attachmentKeys.stream().allMatch(key -> !key.isEmpty())){
            List<Attachment> attachments = attachmentRepository.findAllByKeyIn(attachmentKeys);
            Application finalApplication = application;
            attachments.forEach(attachment -> attachment.setApplication(finalApplication));
            attachmentRepository.saveAll(attachments);
        }
        application = applicationRepository.getById(application.getId());
        return application;
    }
}
