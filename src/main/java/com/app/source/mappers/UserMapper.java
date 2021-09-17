package com.app.source.mappers;

import com.app.source.entities.Account;
import com.app.source.payload.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(Account account);

    Account userDTOToUser(UserDTO userDTO);
}
