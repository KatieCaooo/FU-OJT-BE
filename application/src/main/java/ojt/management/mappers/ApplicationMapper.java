package ojt.management.mappers;

import ojt.management.common.payload.dto.ApplicationDTO;
import ojt.management.data.entities.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    ApplicationDTO applicationToApplicationDTO(Application application);
}
