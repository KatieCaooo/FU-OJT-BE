package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterCreateRequest {
    @NotNull
    @NotBlank
    @Max(255)
    private String name;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
