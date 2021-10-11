package ojt.management.business.services;

import ojt.management.common.exceptions.CrudException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.request.JobCreateRequest;
import ojt.management.common.payload.request.JobUpdateRequest;
import ojt.management.data.entities.Job;

import java.util.List;

public interface JobService {

    List<Job> searchJobs(String name, String title, Long semesterId, Long majorId, Long accountId);

    Job getById(Long id, Long accountId) throws JobNotExistedException;

    Job updateJob(JobUpdateRequest jobUpdateRequest, Long accountId) throws CrudException;

    boolean deleteJob(Long id, Long accountId) throws JobNotExistedException;

    Job createJob(JobCreateRequest jobCreateRequest, Long accountId) throws CrudException;
}
