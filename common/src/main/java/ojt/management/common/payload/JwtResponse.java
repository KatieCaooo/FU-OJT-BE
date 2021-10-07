package ojt.management.common.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.management.common.payload.dto.UserDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private Long id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String email;
    private List<String> roles;
    private String type = "Bearer";
    private UserDTO account;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles, UserDTO account) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.account = account;
    }
}
