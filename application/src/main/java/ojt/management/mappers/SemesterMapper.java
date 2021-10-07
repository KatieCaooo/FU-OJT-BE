package ojt.management.mappers;
import ojt.management.common.payload.dto.SemesterDTO;
import ojt.management.data.entities.Semester;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
    SemesterDTO semesterToSemesterDTO(Semester semester);

    Semester semesterDTOToSemester(SemesterDTO semesterDTO);
}
