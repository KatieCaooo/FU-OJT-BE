package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.CompanyService;
import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.CompanyDTO;
import ojt.management.common.payload.request.CompanyRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.data.entities.Company;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.CompanyMapper;
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
@RequestMapping("/companies")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    private final CompanyMapper companyMapper;
    private final CompanyService companyService;

    public CompanyController(CompanyMapper companyMapper,
                             CompanyService companyService) {
        this.companyMapper = companyMapper;
        this.companyService = companyService;
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'COMPANY_REPRESENTATIVE', 'SYS_ADMIN')")
    @GetMapping()
    public PagedDataResponse<CompanyDTO> searchUser(@RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                    @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Company> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Company> pagedResult = companyService.searchCompany(spec, pageable);
        List<CompanyDTO> data = pagedResult.getContent().stream().map(companyMapper::companyToCompanyDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    public CompanyDTO getCompanyId(@PathVariable Long id,
                                   Authentication authentication) throws CrudException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return companyMapper.companyToCompanyDTO(companyService.getCompanyById(id, accountId));
    }

    @PreAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN')")
    @PutMapping("/{id}")
    public CompanyDTO updateCompany(@PathVariable Long id,
                                    @RequestBody @Valid CompanyRequest companyUpdateRequest,
                                    Authentication authentication)
            throws CrudException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return companyMapper.companyToCompanyDTO(companyService.updateCompany(id, companyUpdateRequest, accountId));
    }
}
