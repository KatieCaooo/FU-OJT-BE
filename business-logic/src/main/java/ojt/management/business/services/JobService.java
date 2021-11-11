package ojt.management.business.services;

import ojt.management.common.exceptions.CrudException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.request.JobCreateRequest;
import ojt.management.common.payload.request.JobRequest;
import ojt.management.data.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface JobService {

    Page<Job> searchJob(Specification<Job> specification, Pageable pageable);

    Job getById(Long id, Long accountId) throws JobNotExistedException;

    Job updateJob(Long id, JobRequest jobUpdateRequest, Long accountId) throws CrudException;

    boolean deleteJob(Long id, Long accountId) throws JobNotExistedException;

    Job createJob(JobCreateRequest jobCreateRequest, Long accountId) throws CrudException;
}
