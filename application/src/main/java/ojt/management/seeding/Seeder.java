package ojt.management.seeding;

import ojt.management.business.services.EmailService;
import ojt.management.business.services.JobService;
import ojt.management.common.exceptions.EmailAlreadyExistedException;
import ojt.management.common.exceptions.EmptyRoleException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.common.exceptions.UsernameAlreadyExistedException;
import ojt.management.controllers.AuthController;
import ojt.management.data.entities.Company;
import ojt.management.data.entities.Job;
import ojt.management.data.entities.Major;
import ojt.management.data.entities.Semester;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import ojt.management.data.repositories.JobRepository;
import ojt.management.data.repositories.MajorRepository;
import ojt.management.data.repositories.SemesterRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class Seeder {

    private final AuthController authController;
    private final JobService jobService;
    private final AccountRepository accountRepository;
    private final MajorRepository majorRepository;
    private final SemesterRepository semesterRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;
    private final CompanyRepository companyRepository;
    private final SpringTemplateEngine templateEngine;


    public Seeder(AuthController authController,
                  JobService jobService,
                  AccountRepository accountRepository,
                  MajorRepository majorRepository,
                  SemesterRepository semesterRepository,
                  JobRepository jobRepository,
                  EmailService emailService,
                  CompanyRepository companyRepository, SpringTemplateEngine templateEngine) {
        this.authController = authController;
        this.jobService = jobService;
        this.accountRepository = accountRepository;
        this.majorRepository = majorRepository;
        this.semesterRepository = semesterRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
        this.companyRepository = companyRepository;
        this.templateEngine = templateEngine;
    }

    @EventListener
    private void seed(ApplicationReadyEvent event) {
        System.out.println("Seeding. please wait!");
        if (semesterRepository.count() == 0) {
            seedSemester();
        }
        if (majorRepository.count() == 0) {
            seedMajor();
        }
        if (accountRepository.count() == 0) {
            seedAccount();
        }
//        if (companyRepository.count() == 0) {
//            seedCompany();
//        }
        if (jobRepository.count() == 0) {
            seedJob();
        }
        try {
            emailService.sendMessage("anonymousvhb@gmail.com", "Something", getTemplate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done seeding");
    }

    private void seedSemester() {
        ZonedDateTime today = ZonedDateTime.now();
        List<Semester> semesters = new ArrayList<>();
        int number = 1;
        for (int i = -2; i < 3; i++) {
            Date startDate = Date.from(today.plusMonths(i * 3).toInstant());
            Date endDate = Date.from(today.plusMonths((i + 1) * 3).toInstant());
            semesters.add(new Semester(String.format("Semester %d", number++), startDate, endDate));
        }
        semesterRepository.saveAll(semesters);
    }

    private void seedMajor() {
        majorRepository.saveAll(MajorData.getSeedData());
    }

    private void seedAccount() {
        UserData.getSeedData().stream().forEach(accountRequest -> {
            try {
                authController.registerUser(accountRequest);
            } catch (UsernameAlreadyExistedException |
                    EmailAlreadyExistedException |
                    EmptyRoleException |
                    MajorNotExistedException |
                    SemesterNotExistedException e) {
                e.printStackTrace();
            }
        });
    }

//    private void seedCompany() {
//        companyRepository.saveAll(CompanyData.getSeedData());
//    }

    private void seedJob() {
        List<Company> companies = companyRepository.findAll();
        List<Job> jobs = JobData.getSeedData();
        jobs.forEach(job -> {
            Random r = new Random();
            Company company = companies.get(r.nextInt(companies.size()));
            job.setCompany(company);
        });
        jobs = jobRepository.saveAll(jobs);
        jobs.forEach(job -> job.getSemesters().addAll(semesterRepository.findAll()));
        jobs.forEach(job -> job.getMajors().addAll(majorRepository.findAll()));
        jobRepository.saveAll(jobs);
    }

    private String getTemplate() {
        Context context = new Context();
        return templateEngine.process("welcome.html", context);
    }
}
