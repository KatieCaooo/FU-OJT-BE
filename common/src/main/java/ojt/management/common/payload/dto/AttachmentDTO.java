package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO implements Serializable{

    private String key;

    private String name;

    private Long accountId;

    private ApplicationDTO applicationDTO;
}
