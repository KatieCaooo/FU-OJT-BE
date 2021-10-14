package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.CompanyService;
import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.dto.CompanyDTO;
import ojt.management.common.payload.request.CompanyCreateRequest;
import ojt.management.common.payload.request.CompanyUpdateRequest;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.mappers.CompanyMapper;
import org.springframework.security.access.prepost.PostAuthorize;
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

    @PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @GetMapping()
    public List<CompanyDTO> searchCompany(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "description", required = false) String description) {
        return companyService.searchCompany(name,
                description).stream().map(companyMapper::companyToCompanyDTO).collect(Collectors.toList());
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    public CompanyDTO getCompanyId(@PathVariable Long id,
                                   Authentication authentication) throws CrudException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return companyMapper.companyToCompanyDTO(companyService.getCompanyById(id,accountId));
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN')")
    @PutMapping("/{id}")
    public CompanyDTO updateCompany(@RequestBody @Valid CompanyUpdateRequest companyUpdateRequest) throws CrudException {
        return companyMapper.companyToCompanyDTO(companyService.updateCompany(companyUpdateRequest));
    }

    @PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PostMapping()
    public CompanyDTO createCompany(@RequestBody @Valid CompanyCreateRequest companyCreateRequest) {
        return companyMapper.companyToCompanyDTO(companyService.createCompany(companyCreateRequest));
    }
}
