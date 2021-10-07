package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateRequest {
    @NotNull
    private Long id;

    @NotNull
    @Max(13)
    @Min(10)
    private String phone;

    @Max(500)
    private String address;

    @NotNull
    @NotBlank
    @Max(1024)
    private String password;
}
