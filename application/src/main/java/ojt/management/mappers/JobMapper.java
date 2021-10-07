package ojt.management.mappers;
import ojt.management.common.payload.dto.JobDTO;
import ojt.management.data.entities.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobDTO jobToJobDTO (Job job);

    Job jobDTOToJob (JobDTO jobDTO);
}
