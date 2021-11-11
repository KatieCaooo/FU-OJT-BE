package ojt.management.mappers;

import ojt.management.common.payload.dto.EvaluationDTO;
import ojt.management.data.entities.Evaluation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);

}
