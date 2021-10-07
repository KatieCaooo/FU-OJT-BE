package ojt.management.mappers;

import ojt.management.common.payload.dto.StudentDTO;
import ojt.management.data.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "semester", target = "semester")
    StudentDTO studentToStudentDTO(Student student);
}
