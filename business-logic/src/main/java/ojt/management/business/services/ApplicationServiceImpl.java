package ojt.management.business.services;

import ojt.management.common.exceptions.AccountNotExistedException;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.ApplicationCreateRequest;
import ojt.management.common.payload.request.ApplicationUpdateRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.entities.Application;
import ojt.management.data.entities.Job;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.ApplicationRepository;
import ojt.management.data.repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  JobRepository jobRepository,
                                  AccountRepository accountRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Application> searchApplication(Specification<Application> specification, Pageable pageable) {
        return applicationRepository.findAll(specification, pageable);
    }

    @Override
    public Application getAppById(Long id, Long accountId)
            throws ApplicationNotExistedException, AccountNotExistedException, NotPermissionException {
        if (Boolean.FALSE.equals(applicationRepository.existsById(id))) {
            throw new ApplicationNotExistedException();
        }
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountNotExistedException();
        }
        Account account = accountRepository.getById(accountId);
        Application application = applicationRepository.getById(id);
        if (account.getRepresentative() != null) {
            if (!application.getJob().getCompany().equals(account.getRepresentative().getCompany())){
                throw new NotPermissionException();
            }
        } else if (account.getStudent() != null) {
            if (!application.getStudent().equals(account.getStudent())){
                throw new NotPermissionException();
            }
        }
        return application;
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
            throws ApplicationNotExistedException, NotPermissionException {
        if (Boolean.FALSE.equals(applicationRepository.existsById(id))) {
            throw new ApplicationNotExistedException();
        }
        Application application = applicationRepository.getById(id);
        if (application.isDisabled()) {
            throw new ApplicationNotExistedException();
        }

        Account account = accountRepository.getById(accountId);
        //Company accept application
        //Company id of application == company id of account
        if (account.getRepresentative() != null) {
            if (application.getJob().getCompany().equals(account.getRepresentative().getCompany())) {
                if (!application.isStudentConfirmed()) {
                    application.setCompanyAccepted(applicationUpdateRequest.isCompanyAccepted());
                    application.setAcceptedAt(new Timestamp(System.currentTimeMillis()));
                } else {
                    throw new NotPermissionException();
                }
            }
        } else if (account.getStudent() != null) {
            //student account id of application == student account id of account
            if (application.getStudent().getAccount().equals(account.getStudent().getAccount())) {
                //Student confirm application
                if (application.isCompanyAccepted()) {
                    application.setStudentConfirmed(applicationUpdateRequest.isStudentConfirmed());
                    application.setConfirmedAt(new Timestamp(System.currentTimeMillis()));
                }
                //Student update exp
                application.setExperience(applicationUpdateRequest.getExperience());
            }
        }
        return applicationRepository.save(application);
    }

    @Override
    public Application createApplication(ApplicationCreateRequest applicationCreateRequest, Long accountId) {
        Account account = accountRepository.getById(accountId);
        Job job = jobRepository.getById(applicationCreateRequest.getJobId());
        Application application = new Application();
        application.setExperience(applicationCreateRequest.getExperience());
        application.setJob(job);
        application.setStudent(account.getStudent());
        application.setStudentConfirmed(false);
        application.setCompanyAccepted(false);
        applicationRepository.save(application);
        return application;
    }
}
