package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest implements Serializable {
    @Email(message = "Email is not valid")
    @NotNull
    @Size(max = 320)
    private String email;

    @NotNull
    @Size(min = 8, max = 1024)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @NotBlank
    private String role;

    @NotNull
    @Size(min = 11, max = 13)
    private String phone;

    /**
     * Start of company Section
     **/
    private Long companyId;
    /**
     * End of company Section
     **/

    /**
     * Start of student Section
     **/
    @Size(max = 500)
    private String address;

    @Size(max = 10)
    @NotBlank
    @NotNull
    private String studentCode;

    private Long majorId;

    /**
     * End of student Section
     **/


    // Constructor or admin creation
    public SignupRequest(String email, String password, String name, String role, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
    }

    // constructor for representative
    public SignupRequest(String email, String password, String name, String role, String phone, Long companyId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.companyId = companyId;
    }

    // constructor for student
    public SignupRequest(String email, String password, String name, String role, String phone, String address, String studentCode, Long majorId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.studentCode = studentCode;
        this.majorId = majorId;
    }
}
