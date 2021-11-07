package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.AttachmentAlreadyExistedException;
import ojt.management.common.exceptions.AttachmentNotExistedException;
import ojt.management.common.payload.request.AttachmentRequest;
import ojt.management.data.entities.Attachment;

import java.util.List;

public interface AttachmentService {

    Attachment getAttachmentByKey(String key, Long accountId) throws AttachmentNotExistedException;

    Attachment createAttachment(AttachmentRequest attachmentRequest, Long accountId)
            throws AccountIdNotExistedException, AttachmentAlreadyExistedException, ApplicationNotExistedException;
}
