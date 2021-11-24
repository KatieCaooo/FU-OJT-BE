package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.management.data.entities.Attachment;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO implements Serializable {
    private Long id;
    private String experience;
    private boolean isCompanyAccepted;
    private boolean isStudentConfirmed;
    private boolean isSchoolDenied;
    private StudentDTO student;
    private JobDTO job;
    private Set<AttachmentDTO> attachments;
}
