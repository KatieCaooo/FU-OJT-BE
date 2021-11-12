package ojt.management.common.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResponse extends Response{
    private String password;

    public PasswordResponse(String status, String message, String password){
        super(status, message);
        this.password = password;
    }
}
