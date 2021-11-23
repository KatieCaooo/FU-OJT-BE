package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
}
