package ojt.management.common.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ojt.management.common.payload.dto.UserDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String refreshToken;
    private Long id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String email;
    private List<String> roles;
    private String type = "Bearer";
    private UserDTO account;

    public JwtResponse(String token, String refreshToken, Long id, String username, String email, List<String> roles, UserDTO account) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.account = account;
    }
}
