package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.ApplicationService;
import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.ApplicationDTO;
import ojt.management.common.payload.request.ApplicationCreateRequest;
import ojt.management.common.payload.request.ApplicationUpdateRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.data.entities.Application;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.ApplicationMapper;
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
@RequestMapping("/applications")
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    public ApplicationController(ApplicationService applicationService,
                                 ApplicationMapper applicationMapper) {
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE', 'STUDENT')")
    @GetMapping()
    public PagedDataResponse<ApplicationDTO> searchApplication(@RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                               @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Application> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Application> pagedResult = applicationService.searchApplication(spec, pageable);
        List<ApplicationDTO> data = pagedResult.getContent().stream().map(applicationMapper::applicationToApplicationDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'COMPANY_REPRESENTATIVE', 'STUDENT')")
    @GetMapping("/{id}")
    public ApplicationDTO getAppById(@PathVariable Long id,
                                     Authentication authentication)
            throws ApplicationNotExistedException, AccountIdNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return applicationMapper.applicationToApplicationDTO(applicationService.getAppById(id, accountId));
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE', 'STUDENT')")
    @PutMapping("/{id}")
    public ApplicationDTO updateApplication(@Valid @RequestBody ApplicationUpdateRequest applicationUpdateRequest,
                                            @PathVariable Long id,
                                            Authentication authentication)
            throws ApplicationNotExistedException, NotPermissionException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return applicationMapper.applicationToApplicationDTO(
                applicationService.updateApplication(id, applicationUpdateRequest, accountId));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @DeleteMapping("/{id}")
    public boolean deleteApplication(@PathVariable Long id,
                                     Authentication authentication)
            throws ApplicationNotExistedException, NotPermissionException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return applicationService.deleteApplication(id, accountId);
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @PostMapping()
    public ApplicationDTO createApplication(@Valid @RequestBody ApplicationCreateRequest applicationCreateRequest,
                                            Authentication authentication) {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return applicationMapper.applicationToApplicationDTO(
                applicationService.createApplication(applicationCreateRequest, accountId));
    }
}
