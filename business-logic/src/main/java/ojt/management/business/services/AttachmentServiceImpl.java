package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.AttachmentAlreadyExistedException;
import ojt.management.common.exceptions.AttachmentNotExistedException;
import ojt.management.common.payload.request.AttachmentRequest;
import ojt.management.data.entities.Attachment;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.ApplicationRepository;
import ojt.management.data.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;
    private final ApplicationRepository applicationRepository;
    private final AccountRepository accountRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 ApplicationRepository applicationRepository,
                                 AccountRepository accountRepository){
        this.attachmentRepository = attachmentRepository;
        this.accountRepository = accountRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Attachment getAttachmentByKey(String key, Long accountId)
            throws AttachmentNotExistedException {
        Attachment attachment = attachmentRepository.getAttachmentByKey(key);
        if (attachment == null) {
            throw new AttachmentNotExistedException();
        }
        else {
            return attachment;
        }
    }

    @Override
    public Attachment createAttachment(AttachmentRequest attachmentRequest, Long accountId)
            throws AccountIdNotExistedException, AttachmentAlreadyExistedException, ApplicationNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(attachmentRequest.getAccountId()))){
            throw new AccountIdNotExistedException();
        }
        if (Boolean.FALSE.equals(applicationRepository.existsById(attachmentRequest.getApplicationId()))){
            throw new ApplicationNotExistedException();
        }
        if (Boolean.TRUE.equals(attachmentRepository.existsAttachmentByApplicationIdOrKey(attachmentRequest.getApplicationId(), attachmentRequest.getKey()))){
            throw new AttachmentAlreadyExistedException();
        }
        Attachment attachment = new Attachment();
        attachment.setKey(attachmentRequest.getKey());
        attachment.setApplication(applicationRepository.getById(attachmentRequest.getApplicationId()));
        attachment.setAccountId(attachmentRequest.getAccountId());
        attachment.setName(attachmentRequest.getName());
        attachmentRepository.save(attachment);
        return attachment;
    }
}
