package ojt.management.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.management.enums.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    private RoleEnum name;
}
