package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.SemesterService;
import ojt.management.common.exceptions.SemesterAlreadyExistedException;
import ojt.management.common.exceptions.SemesterDisabledException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.SemesterDTO;
import ojt.management.common.payload.request.SemesterRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.data.entities.Semester;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.SemesterMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/semesters")
@SecurityRequirement(name = "bearerAuth")
public class SemesterController {

    private final SemesterService semesterService;
    private final SemesterMapper semesterMapper;

    public SemesterController(SemesterService semesterService, SemesterMapper semesterMapper) {
        this.semesterService = semesterService;
        this.semesterMapper = semesterMapper;
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'STUDENT', 'COMPANY_REPRESENTATIVE')")
    @GetMapping("/{id}")
    public SemesterDTO getById(@PathVariable Long id) throws SemesterNotExistedException {
        return semesterMapper.semesterToSemesterDTO(semesterService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'STUDENT', 'COMPANY_REPRESENTATIVE')")
    @GetMapping()
    public PagedDataResponse<SemesterDTO> searchUser(@RequestParam(value = "search", required = false) String search,
                                                     @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                     @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Semester> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Semester> pagedResult = semesterService.searchSemester(spec, pageable);
        List<SemesterDTO> data = pagedResult.getContent().stream().map(semesterMapper::semesterToSemesterDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PutMapping("/{id}")
    public SemesterDTO updateSemester(@PathVariable Long id,
                                      @Valid @RequestBody SemesterRequest semesterUpdateRequest)
            throws SemesterAlreadyExistedException, SemesterNotExistedException {
        return semesterMapper.semesterToSemesterDTO(semesterService.updateSemester(id, semesterUpdateRequest));
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteSemester(@PathVariable Long id)
            throws SemesterNotExistedException, SemesterDisabledException {
        return semesterService.deleteSemester(id);
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PostMapping()
    public SemesterDTO createSemester(@Valid @RequestBody SemesterRequest semesterRequest)
            throws SemesterAlreadyExistedException {
        return semesterMapper.semesterToSemesterDTO(semesterService.createSemester(semesterRequest));
    }
}
