package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.EvaluationIdNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.EvaluationCreateRequest;
import ojt.management.common.payload.request.EvaluationUpdateRequest;
import ojt.management.data.entities.Application;
import ojt.management.data.entities.Evaluation;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.ApplicationRepository;
import ojt.management.data.repositories.EvaluationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final AccountRepository accountRepository;
    private final ApplicationRepository applicationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository,
                                 AccountRepository accountRepository, ApplicationRepository applicationRepository) {
        this.evaluationRepository = evaluationRepository;
        this.accountRepository = accountRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Page<Evaluation> searchEvaluation(Specification<Evaluation> specification, Pageable pageable) {
        return evaluationRepository.findAll(specification, pageable);
    }

    @Override
    public Evaluation getEvaluationById(Long id, Long accountId)
            throws EvaluationIdNotExistedException, AccountIdNotExistedException {
        if (Boolean.FALSE.equals(evaluationRepository.existsById(id))) {
            throw new EvaluationIdNotExistedException();
        }
        if (Boolean.FALSE.equals(accountRepository.existsById(accountId))) {
            throw new AccountIdNotExistedException();
        }
        if (accountRepository.getById(accountId).getRepresentative() != null) {
            return evaluationRepository.getEvaluationRep(accountRepository.getById(accountId).getRepresentative().getCompany().getId(), id);
        } else {
            return evaluationRepository.getEvaluationStudent(accountRepository.getById(accountId).getStudent().getId(), id);
        }
    }

    @Override
    public Evaluation updateEvaluation(Long id, EvaluationUpdateRequest evaluationUpdateRequest, Long accountId)
            throws EvaluationIdNotExistedException {
        if (Boolean.FALSE.equals(evaluationRepository.existsById(id))) {
            throw new EvaluationIdNotExistedException();
        }

        Evaluation evaluation = evaluationRepository.getEvaluationRep(accountRepository.getById(accountId).getRepresentative().getCompany().getId(), id);
        evaluation.setComment(evaluationUpdateRequest.getComment());
        evaluation.setGrade(evaluationUpdateRequest.getGrade());
        evaluation.setPass(evaluationUpdateRequest.isPass());
        evaluationRepository.save(evaluation);
        return evaluation;
    }

    @Override
    public Evaluation createEvaluation(EvaluationCreateRequest evaluationCreateRequest, Long accountId)
            throws NotPermissionException {
        Evaluation evaluation = new Evaluation();
        Long accountCompanyId = accountRepository.getById(accountId).getRepresentative().getCompany().getId();
        Application application = applicationRepository.getById(evaluationCreateRequest.getApplicationId());
        if (application.getJob().getCompany().getId() == accountCompanyId) {
            evaluation.setGrade(evaluationCreateRequest.getGrade());
            evaluation.setComment(evaluationCreateRequest.getComment());
            evaluation.setPass(evaluationCreateRequest.isPass());
            evaluation.setApplication(application);
            evaluation = evaluationRepository.save(evaluation);
            application.setEvaluation(evaluation);
            applicationRepository.save(application);
            return evaluation;
        } else {
            throw new NotPermissionException();
        }
    }
}
