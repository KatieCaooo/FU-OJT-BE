package ojt.management.controllers;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.MajorService;
import ojt.management.common.exceptions.MajorNameAlreadyExistedException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.common.payload.PagedDataResponse;
import ojt.management.common.payload.dto.MajorDTO;
import ojt.management.common.payload.request.MajorDeletionRequest;
import ojt.management.common.payload.request.MajorRequest;
import ojt.management.common.utils.SortUtils;
import ojt.management.data.entities.Major;
import ojt.management.data.rsql.CustomRsqlVisitor;
import ojt.management.mappers.MajorMapper;
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
@RequestMapping("/majors")
@SecurityRequirement(name = "bearerAuth")
public class MajorController {

    private final MajorService majorService;
    private final MajorMapper majorMapper;

    public MajorController(MajorService majorService, MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
        this.majorService = majorService;
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'STUDENT','COMPANY_REPRESENTATIVE')")
    @GetMapping("/{id}")
    public MajorDTO getMajorById(@PathVariable Long id)
            throws MajorNotExistedException {
        return majorMapper.majorToMajorDTO(majorService.getMajorById(id));
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'STUDENT','COMPANY_REPRESENTATIVE')")
    @GetMapping()
    public PagedDataResponse<MajorDTO> searchMajor(@RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                                    @RequestParam(value = "sortBy", required = false, defaultValue = "id ASC") String sortBy) {
        Specification<Major> spec = Specification.where(null);
        if (Strings.isNotBlank(search)) {
            Node rootNode = new RSQLParser().parse(search);
            spec = rootNode.accept(new CustomRsqlVisitor<>());
        }
        Sort sort = SortUtils.parseSortQuery(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Major> pagedResult = majorService.searchMajor(spec, pageable);
        List<MajorDTO> data = pagedResult.getContent().stream().map(majorMapper::majorToMajorDTO).collect(Collectors.toList());

        return new PagedDataResponse<>("OK", "Retrieved account successfully.", data, pagedResult.getTotalElements(), pagedResult.getTotalPages(), pagedResult.getNumber());
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PutMapping("/{id}")
    public MajorDTO updateMajor(@PathVariable Long id,
                                @Valid @RequestBody MajorRequest majorUpdateRequest)
            throws MajorNotExistedException, MajorNameAlreadyExistedException {
        return majorMapper.majorToMajorDTO(majorService.updateMajor(id, majorUpdateRequest));
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteMajor(@PathVariable Long id) throws MajorNotExistedException {
        return majorService.deleteMajor(id);
    }

    @DeleteMapping
    public boolean deleteMajors(@RequestBody MajorDeletionRequest majorDeletionRequest) throws MajorNotExistedException {
        return majorService.deleteMajors(majorDeletionRequest.getIds());
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PatchMapping("/{id}/recover")
    public boolean recoverMajor(@PathVariable Long id) throws MajorNotExistedException {
        return majorService.recoverMajor(id);
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PostMapping()
    public MajorDTO createMajor(@RequestBody MajorRequest majorRequest)
            throws MajorNameAlreadyExistedException {
        return majorMapper.majorToMajorDTO(majorService.createMajor(majorRequest.getName()));
    }
}
