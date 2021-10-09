package ojt.management.business.services;

import ojt.management.common.exceptions.CrudException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.request.JobRequest;
import ojt.management.common.payload.request.JobUpdateRequest;
import ojt.management.data.entities.Job;

import java.util.List;

public interface JobService {

    List<Job> searchJobs(String name, String title, Long semesterId, Long majorId);

    Job getById(Long id) throws JobNotExistedException;

    Job updateJob(JobUpdateRequest jobUpdateRequest) throws CrudException;

    boolean deleteJob(Long id) throws JobNotExistedException;

    Job createJob(JobRequest jobCreateRequest) throws CrudException;
}
