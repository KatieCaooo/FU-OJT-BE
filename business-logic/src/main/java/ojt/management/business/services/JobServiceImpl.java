package ojt.management.business.services;
import ojt.management.common.exceptions.JobNameAlreadyExistedException;
import ojt.management.common.exceptions.JobNotExistedException;
import ojt.management.common.payload.request.LoginRequest;
import ojt.management.data.entities.*;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.JobRepository;
import ojt.management.data.repositories.MajorRepository;
import ojt.management.data.repositories.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final SemesterRepository semesterRepository;
    private final MajorRepository majorRepository;

    public JobServiceImpl(JobRepository jobRepository, AccountRepository accountRepository, SemesterRepository semesterRepository, MajorRepository majorRepository) {
        this.jobRepository = jobRepository;
        this.accountRepository = accountRepository;
        this.semesterRepository = semesterRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public List<Job> searchJobs(String name, String description, String title, String semesters, String majors) {
        if (name == null & description == null & title == null & semesters == null & majors == null) {
            return jobRepository.findAll();
        } else {
            return jobRepository.searchJob(name, description, title, semesters, majors);
        }
    }

    @Override
    public Job getById(Long id) throws JobNotExistedException{
        if (Boolean.FALSE.equals(jobRepository.existsById(id))) {
            throw new JobNotExistedException();
        } else {
            return jobRepository.getById(id);
        }
    }

    @Override
    public Job updateJob(Long id, String name, String description, String title, String semesters, String majors) throws JobNotExistedException, JobNameAlreadyExistedException {
        if (Boolean.FALSE.equals(jobRepository.existsById(id))) {
            throw new JobNotExistedException();
        } else if (Boolean.TRUE.equals(jobRepository.existsByName(name))) {
            throw new JobNameAlreadyExistedException();
        } else {
            Job job = jobRepository.getById(id);
            if (job.isDisabled() == true) {
                throw new JobNotExistedException();
            } else {
                if (name != null)
                    job.setName(name);
                if (description != null)
                    job.setDescription(description);
                if (title != null)
                    job.setTitle(title);
                if (semesters != null) {
                    Semester semester = semesterRepository.findByName(semesters);
                    Set<Semester> semesterSet = new HashSet<>();
                    semesterSet.add(semester);
                    job.setSemesters(semesterSet);
                }
                if (majors != null) {
                    Major major = majorRepository.findByName(majors);
                    Set<Major> majorSet = new HashSet<>();
                    majorSet.add(major);
                    job.setMajors(majorSet);
                }
                jobRepository.save(job);
                return jobRepository.getById(id);
            }
        }
    }

    @Override
    public boolean deleteJob(Long id) throws JobNotExistedException{
        if (Boolean.FALSE.equals(jobRepository.existsById(id))) {
            throw new JobNotExistedException();
        } else {
            boolean response = false;
            Job job = jobRepository.getById(id);
            if (job != null & job.isDisabled() == false) {
                job.setDisabled(true);
                jobRepository.save(job);
                response = true;
                return response;
            } else
                return response;
        }
    }

    @Override
    public Job createJob(String name, String description, String title, String semesters, String majors) throws JobNameAlreadyExistedException{
        if (Boolean.TRUE.equals(jobRepository.existsByName(name))) {
            throw new JobNameAlreadyExistedException();
        } else {
            LoginRequest loginRequest = new LoginRequest();
            Account account = accountRepository.findByEmail(loginRequest.getEmail());
            Company company = account.getRepresentative().getCompany();
            Job job = new Job(name, description, title);
            Set<Semester> semesterSet = new HashSet<>();
            Semester semester = semesterRepository.findByName(semesters);
            semesterSet.add(semester);
            job.setSemesters(semesterSet);
            Major major = majorRepository.findByName(majors);
            Set<Major> majorSet = new HashSet<>();
            majorSet.add(major);
            job.setMajors(majorSet);
            job.setCompany(company);
            jobRepository.save(job);
            return jobRepository.getById(job.getId());
        }
    }

}
