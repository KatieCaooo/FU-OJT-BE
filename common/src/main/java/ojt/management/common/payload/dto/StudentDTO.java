package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {
    private Long id;
    private String address;
    private String studentCode;
    private MajorDTO major;
    private SemesterDTO semester;
}
