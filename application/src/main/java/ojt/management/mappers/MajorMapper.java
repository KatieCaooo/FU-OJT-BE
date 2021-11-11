package ojt.management.mappers;


import ojt.management.common.payload.dto.MajorDTO;
import ojt.management.data.entities.Major;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MajorMapper {
    MajorDTO majorToMajorDTO(Major major);
}
