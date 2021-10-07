package ojt.management.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String role;
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
    private String address;
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
