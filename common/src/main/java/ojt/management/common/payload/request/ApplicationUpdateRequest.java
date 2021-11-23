package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUpdateRequest extends ApplicationCreateRequest implements Serializable {
    @NotNull
    private boolean isSchoolDenied;

    @NotNull
    private boolean isCompanyAccepted;

    @NotNull
    private boolean isStudentConfirmed;
}
