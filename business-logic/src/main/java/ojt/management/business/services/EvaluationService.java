package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.EvaluationIdNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.EvaluationCreateRequest;
import ojt.management.common.payload.request.EvaluationUpdateRequest;
import ojt.management.data.entities.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface EvaluationService {
    Evaluation getEvaluationById(Long id, Long accountId)
            throws EvaluationIdNotExistedException, AccountIdNotExistedException;

    Page<Evaluation> searchEvaluation(Specification<Evaluation> specification, Pageable pageable);

    Evaluation updateEvaluation(Long id, EvaluationUpdateRequest evaluationUpdateRequest, Long accountId)
            throws EvaluationIdNotExistedException;

    Evaluation createEvaluation(EvaluationCreateRequest evaluationCreateRequest, Long accountId)
            throws NotPermissionException;
}
