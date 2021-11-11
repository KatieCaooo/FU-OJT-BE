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
public class AccountRequest implements Serializable {
    @Email(message = "Email is not valid")
    @NotNull
    @Size(max = 320)
    private String email;

    @NotNull
    @Size(min = 8, max = 1024)
    private String password;

    @Size(max = 2048)
    private String avatar;

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
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String companyName;

    @NotNull
    @NotBlank
    @Size(max = 2000)
    private String description;

    @Size(max = 500)
    private String companyAddress;
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

    private Long semesterId;

    /**
     * End of student Section
     **/

    // Constructor or admin creation
    public AccountRequest(String email, String password, String name, String role, String phone, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.avatar = avatar;
    }

    // constructor for representative
    public AccountRequest(String email, String password, String name, String role, String companyName, String description, String companyAddress, String phone, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.companyName = companyName;
        this.description = description;
        this.companyAddress = companyAddress;
        this.phone = phone;
        this.avatar = avatar;
    }

    // constructor for student
    public AccountRequest(String email, String password, String name, String role, String phone, String address, String studentCode, Long majorId, Long semesterId, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.studentCode = studentCode;
        this.majorId = majorId;
        this.semesterId = semesterId;
        this.avatar = avatar;

    }

    /**
     * Start of Update Section
     **/
    //constructor for admin update student
    public AccountRequest(String email, String password, String name, String phone, String address, String studentCode, Long majorId, Long semesterId, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.studentCode = studentCode;
        this.majorId = majorId;
        this.semesterId = semesterId;
        this.avatar = avatar;

    }

    //constructor for admin update rep
    public AccountRequest(String email, String password, String name, String phone, String avatar) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;

    }

    //constructor for student update
    public AccountRequest(String password, String phone, String address, String avatar) {
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;

    }
    /**
     * End of Update Section
     **/
}
