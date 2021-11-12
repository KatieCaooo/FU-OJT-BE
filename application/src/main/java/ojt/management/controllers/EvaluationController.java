package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.EvaluationService;
import ojt.management.common.exceptions.AccountNotExistedException;
import ojt.management.common.exceptions.EvaluationIdNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.EvaluationDTO;
import ojt.management.common.payload.request.EvaluationCreateRequest;
import ojt.management.common.payload.request.EvaluationUpdateRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.data.entities.Evaluation;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.EvaluationMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluations")
@SecurityRequirement(name = "bearerAuth")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final EvaluationMapper evaluationMapper;

    public EvaluationController(EvaluationMapper evaluationMapper,
                                EvaluationService evaluationService) {
        this.evaluationMapper = evaluationMapper;
        this.evaluationService = evaluationService;
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE', 'STUDENT')")
    @GetMapping()
    public PagedDataResponse<EvaluationDTO> searchEvaluation(@RequestParam(value = "search", required = false) String search,
                                                             @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                             @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Evaluation> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Evaluation> pagedResult = evaluationService.searchEvaluation(spec, pageable);
        List<EvaluationDTO> data = pagedResult.getContent().stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE', 'STUDENT')")
    @GetMapping("/{id}")
    public EvaluationDTO getEvaluationById(@PathVariable Long id,
                                           Authentication authentication)
            throws EvaluationIdNotExistedException, AccountNotExistedException, NotPermissionException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return evaluationMapper.evaluationToEvaluationDTO(evaluationService.getEvaluationById(id, accountId));
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @PutMapping("/{id}")
    public EvaluationDTO updateEvaluation(@PathVariable Long id,
                                          @RequestBody @Valid EvaluationUpdateRequest evaluationUpdateRequest,
                                          Authentication authentication)
            throws EvaluationIdNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return evaluationMapper.evaluationToEvaluationDTO(
                evaluationService.updateEvaluation(id, evaluationUpdateRequest, accountId));
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @PostMapping()
    public EvaluationDTO createEvaluation(@RequestBody @Valid EvaluationCreateRequest evaluationCreateRequest,
                                          Authentication authentication) throws NotPermissionException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return evaluationMapper.evaluationToEvaluationDTO(
                evaluationService.createEvaluation(evaluationCreateRequest, accountId));
    }
}
