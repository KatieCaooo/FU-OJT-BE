package ojt.management.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRequest implements Serializable{
    @NotNull
    @NotBlank
    private String key;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Long accountId;

    @NotNull
    private Long applicationId;
}
