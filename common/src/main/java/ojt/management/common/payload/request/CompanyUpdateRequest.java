package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateRequest {
    @NotNull
    @NotBlank
    @Max(255)
    private String name;

    @NotNull
    @NotBlank
    @Max(500)
    private String description;
}
