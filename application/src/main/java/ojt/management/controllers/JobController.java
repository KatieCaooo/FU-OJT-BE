package ojt.management.controllers;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.JobService;
import ojt.management.common.exceptions.JobNameAlreadyExistedException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.dto.JobDTO;
import ojt.management.common.payload.request.JobCreateRequest;
import ojt.management.common.payload.request.JobUpdateRequest;
import ojt.management.data.entities.Major;
import ojt.management.data.entities.Semester;
import ojt.management.mappers.JobMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE','SYS_ADMIN', 'STUDENT')")
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

    @GetMapping()
    public List<JobDTO> searchJobs(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "semesters", required = false) String semesters,
                                   @RequestParam(value = "majors", required = false) String major) {
        return jobService.searchJobs(name, description, title, semesters, major).stream().map(jobMapper::jobToJobDTO).collect(Collectors.toList());
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @GetMapping("/{id}")
    public JobDTO getById(@PathVariable Long id) throws JobNotExistedException {
        return jobMapper.jobToJobDTO(jobService.getById(id));
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @PutMapping("/{id}")
    public JobDTO updateJob(@Valid @RequestBody JobUpdateRequest jobUpdateRequest) throws JobNotExistedException, JobNameAlreadyExistedException {
        return jobMapper.jobToJobDTO(jobService.updateJob(jobUpdateRequest.getId(), jobUpdateRequest.getName(),
                jobUpdateRequest.getDescription(), jobUpdateRequest.getTitle(), jobUpdateRequest.getSemesters(), jobUpdateRequest.getMajors()));
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @DeleteMapping("/{id}")
    public boolean deleteJob(@PathVariable Long id) throws JobNotExistedException {
        return jobService.deleteJob(id);
    }

    @PostAuthorize("hasAnyAuthority('COMPANY_REPRESENTATIVE')")
    @PostMapping
    public JobDTO createJob(@Valid @RequestBody JobCreateRequest jobCreateRequest) throws JobNameAlreadyExistedException {
        return jobMapper.jobToJobDTO(jobService.createJob(jobCreateRequest.getName(), jobCreateRequest.getDescription(),
                jobCreateRequest.getTitle(), jobCreateRequest.getSemesters(), jobCreateRequest.getMajors()));
    }
}
