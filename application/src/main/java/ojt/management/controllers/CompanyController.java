package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.CompanyService;
import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.payload.dto.CompanyDTO;
import ojt.management.common.payload.request.CompanyUpdateRequest;
import ojt.management.mappers.CompanyMapper;
import org.springframework.security.access.prepost.PostAuthorize;
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

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @PutMapping("/{id}")
    public CompanyDTO updateCompany(@RequestBody @Valid CompanyUpdateRequest companyUpdateRequest) throws CompanyNotExistedException {
        return companyMapper.companyToCompanyDTO(companyService.updateCompany(companyUpdateRequest));
    }
}
