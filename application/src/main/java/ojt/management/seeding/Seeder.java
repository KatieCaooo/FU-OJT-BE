package ojt.management.seeding;

import ojt.management.common.enums.RoleEnum;
import ojt.management.controllers.AuthController;
import ojt.management.data.entities.Company;
import ojt.management.data.entities.Major;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import ojt.management.data.repositories.MajorRepository;
import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.exceptions.EmailAlreadyExistedException;
import ojt.management.common.exceptions.EmptyRoleException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.common.exceptions.UsernameAlreadyExistedException;
import ojt.management.common.payload.request.SignupRequest;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Seeder {

    private final AuthController authController;
    private final AccountRepository accountRepository;
    private final MajorRepository majorRepository;
    private final CompanyRepository companyRepository;


    public Seeder(AuthController authController, AccountRepository accountRepository, MajorRepository majorRepository, CompanyRepository companyRepository) {
        this.authController = authController;
        this.accountRepository = accountRepository;
        this.majorRepository = majorRepository;
        this.companyRepository = companyRepository;
    }

    @EventListener
    private void seed(ApplicationReadyEvent event) {
        if (companyRepository.count() == 0) {
            seedCompany();
        }
        if (majorRepository.count() == 0) {
            seedMajor();
        }
        if (accountRepository.count() == 0) {
            seedAccount();
        }
    }

    private void seedMajor() {
        List<Major> majors = Arrays.asList(
                new Major("Software Engineering"),
                new Major("Business Administration")
        );
        majorRepository.saveAll(majors);
    }

    private void seedCompany() {
        List<Company> companies = Arrays.asList(
                new Company("Company A", "Description for company A"),
                new Company("Company B", "Description for company B")
        );
        companyRepository.saveAll(companies);
    }

    private void seedAccount() {
        String initialPassword = "123456";
        List<SignupRequest> signupRequests = Arrays.asList(
                new SignupRequest("thanhthu0321@gmail.com", initialPassword, "Thu", RoleEnum.SYS_ADMIN.name(), "0988988796"),
                new SignupRequest("student1@gmail.com", initialPassword, "First Student", RoleEnum.STUDENT.name(), "0123867861", "123 Street, Ward 11, A District, ABC city", "SE150099", Long.valueOf(1)),
                new SignupRequest("student2@gmail.com", initialPassword, "Second Student", RoleEnum.STUDENT.name(), "0123667861", "124 Street, Ward 11, A District, ABC city", "SE150098", Long.valueOf(1)),
                new SignupRequest("student3@gmail.com", initialPassword, "Third Student", RoleEnum.STUDENT.name(), "0123897861", "125 Street, Ward 11, A District, ABC city", "BA150097", Long.valueOf(2)),
                new SignupRequest("representative1@gmail.com", initialPassword, "First Representative", RoleEnum.COMPANY_REPRESENTATIVE.name(), "1237894560", Long.valueOf(1)),
                new SignupRequest("representative2@gmail.com", initialPassword, "Second Representative", RoleEnum.COMPANY_REPRESENTATIVE.name(), "1237894560", Long.valueOf(2)));

        signupRequests.stream().forEach(signupRequest -> {
            try {
                authController.registerUser(signupRequest);
            } catch (UsernameAlreadyExistedException | EmailAlreadyExistedException | EmptyRoleException | CompanyNotExistedException | MajorNotExistedException e) {
                e.printStackTrace();
            }
        });
    }
}
