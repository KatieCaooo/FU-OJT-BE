package ojt.management.mappers;

import ojt.management.common.payload.dto.UserDTO;
import ojt.management.data.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "account", target = "role", qualifiedByName = "role")
    @Mapping(source = "student", target = "student")
    UserDTO userToUserDTO(Account account);

    Account userDTOToUser(UserDTO userDTO);

    @Named(("role"))
    default String convertRole(Account account) {
        String role;
        if (account.isAdmin()) {
            role = "SYS_ADMIN";
        } else if (account.getRepresentative() != null && account.getStudent() == null) {
            role = "COMPANY_REPRESENTATIVE";
        } else {
            role = "STUDENT";
        }
        return role;
    }
}
