package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.JobService;
import ojt.management.common.exceptions.CrudException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.dto.JobDTO;
import ojt.management.common.payload.request.JobCreateRequest;
import ojt.management.common.payload.request.JobUpdateRequest;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.mappers.JobMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobs")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    private final JobService jobService;
    private final JobMapper jobMapper;

    public JobController(JobMapper jobMapper, JobService jobService) {
        this.jobMapper = jobMapper;
        this.jobService = jobService;
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN', 'STUDENT')")
    @GetMapping()
    public List<JobDTO> searchJobs(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "semesterId", required = false) Long semesterId,
                                   @RequestParam(value = "majorId", required = false) Long majorId, Authentication authentication) {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return jobService.searchJobs(name, title, semesterId, majorId, accountId).stream().map(jobMapper::jobToJobDTO).collect(Collectors.toList());
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    public JobDTO getById(@PathVariable Long id, Authentication authentication) throws JobNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return jobMapper.jobToJobDTO(jobService.getById(id, accountId));
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN')")
    @PutMapping("/{id}")
    public JobDTO updateJob(@Valid @RequestBody JobUpdateRequest jobUpdateRequest, Authentication authentication) throws CrudException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return jobMapper.jobToJobDTO(jobService.updateJob(jobUpdateRequest, accountId));
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deleteJob(@PathVariable Long id, Authentication authentication) throws JobNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return jobService.deleteJob(id, accountId);
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE',SYS_ADMIN)")
    @PostMapping
    public JobDTO createJob(@Valid @RequestBody JobCreateRequest jobCreateRequest, Authentication authentication) throws CrudException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return jobMapper.jobToJobDTO(jobService.createJob(jobCreateRequest, accountId));
    }
}
