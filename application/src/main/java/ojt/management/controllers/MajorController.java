package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.MajorService;
import ojt.management.common.exceptions.MajorNameAlreadyExistedException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.common.payload.dto.MajorDTO;
import ojt.management.common.payload.request.MajorUpdateRequest;
import ojt.management.mappers.MajorMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@PostAuthorize("hasAnyAuthority('SYS_ADMIN', 'STUDENT')")
@RequestMapping("/majors")
@SecurityRequirement(name = "bearerAuth")
public class MajorController {

    private final MajorService majorService;
    private final MajorMapper majorMapper;

    public MajorController(MajorService majorService, MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
        this.majorService = majorService;
    }

    @GetMapping("/{id}")
    public MajorDTO getMajorById(@PathVariable Long id) throws MajorNotExistedException {
        return majorMapper.majorToMajorDTO(majorService.getMajorById(id));
    }

    @GetMapping()
    public List<MajorDTO> searchMajor(@RequestParam(value = "name", required = false) String name) {
        return majorService.searchMajor(name).stream().map(majorMapper::majorToMajorDTO).collect(Collectors.toList());
    }

    @PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PutMapping("/{id}")
    public MajorDTO updateMajor(@Valid @RequestBody MajorUpdateRequest majorUpdateRequest) throws MajorNotExistedException, MajorNameAlreadyExistedException {
        return majorMapper.majorToMajorDTO(majorService.updateMajor(majorUpdateRequest.getId(), majorUpdateRequest.getName()));
    }

    @PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteMajor(@PathVariable Long id) throws MajorNotExistedException {
        return majorService.deleteMajor(id);
    }

    @PostAuthorize("hasAnyAuthority('SYS_ADMIN')")
    @PostMapping()
    public MajorDTO createMajor(@RequestBody String name) throws MajorNameAlreadyExistedException {
        return majorMapper.majorToMajorDTO(majorService.createMajor(name));
    }
}
