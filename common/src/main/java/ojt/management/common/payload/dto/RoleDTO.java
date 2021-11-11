package ojt.management.common.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.management.common.enums.RoleEnum;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {
    private Integer id;
    private RoleEnum name;
}
