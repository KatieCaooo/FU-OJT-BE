package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.management.common.payload.dto.AttachmentDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCreateRequest implements Serializable {
    @NotNull
    @NotBlank
    @Size(max = 1000)
    private String experience;

    @NotNull
    private Long jobId;

    private List<AttachmentDTO> attachments;

}
