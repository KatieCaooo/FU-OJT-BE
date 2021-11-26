package ojt.management.mappers;

import ojt.management.common.payload.dto.ApplicationDTO;
import ojt.management.data.entities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "student.account", target = "account")
    ApplicationDTO applicationToApplicationDTO(Application application);
}
