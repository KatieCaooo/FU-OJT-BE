package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest  implements Serializable {
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotNull
    @NotBlank
    private List<Long> semesterIds;

    @NotNull
    @NotBlank
    private List<Long> majorIds;
}
