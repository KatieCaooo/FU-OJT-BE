package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStudentAccountDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String phone;
}
