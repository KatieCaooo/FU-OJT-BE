package ojt.management.mappers;

import ojt.management.common.payload.dto.AttachmentDTO;
import ojt.management.data.entities.Attachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentDTO attachmentToAttachDTO(Attachment attachment);

    Attachment attachmentDTOToAttachment(AttachmentDTO attachmentDTO);
}
