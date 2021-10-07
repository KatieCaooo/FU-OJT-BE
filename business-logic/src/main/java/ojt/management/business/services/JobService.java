package ojt.management.business.services;

import ojt.management.common.exceptions.JobNameAlreadyExistedException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.data.entities.Job;
import ojt.management.data.entities.Major;
import ojt.management.data.entities.Semester;

import java.util.List;
import java.util.Set;

public interface JobService {

    List<Job> searchJobs(String name, String description, String title, String semesters, String major);

    Job getById(Long id) throws JobNotExistedException;

    Job updateJob(Long id, String name, String description, String title, String semesters, String majors) throws JobNotExistedException, JobNameAlreadyExistedException;

    boolean deleteJob(Long id) throws JobNotExistedException;

    Job createJob(String name, String description, String title, String semesters, String majors) throws JobNameAlreadyExistedException;
}
