package ojt.management.seeding;

import com.github.javafaker.Faker;
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
        Faker faker = new Faker();
        String initialPassword = "123456";
        List<SignupRequest> signupRequests = Arrays.asList(
                new SignupRequest("thanhthu0321@gmail.com", initialPassword, "Thu", RoleEnum.SYS_ADMIN.name(), "0988988796"),
                new SignupRequest("student1@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150099", Long.valueOf(1)),
                new SignupRequest("student2@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150098", Long.valueOf(1)),
                new SignupRequest("student3@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150097", Long.valueOf(2)),
                new SignupRequest("student4@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150096", Long.valueOf(2)),
                new SignupRequest("student5@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150095", Long.valueOf(2)),
                new SignupRequest("student6@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150094", Long.valueOf(2)),
                new SignupRequest("student7@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150093", Long.valueOf(2)),
                new SignupRequest("student8@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150092", Long.valueOf(2)),
                new SignupRequest("student9@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150091", Long.valueOf(2)),
                new SignupRequest("student10@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150090", Long.valueOf(2)),
                new SignupRequest("student11@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150102", Long.valueOf(2)),
                new SignupRequest("student12@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150103", Long.valueOf(2)),
                new SignupRequest("student13@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150104", Long.valueOf(2)),
                new SignupRequest("student14@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150105", Long.valueOf(2)),
                new SignupRequest("student15@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150106", Long.valueOf(2)),
                new SignupRequest("student16@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150107", Long.valueOf(2)),
                new SignupRequest("student17@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150108", Long.valueOf(2)),
                new SignupRequest("student18@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150109", Long.valueOf(2)),
                new SignupRequest("student19@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150110", Long.valueOf(2)),
                new SignupRequest("student20@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150111", Long.valueOf(2)),
                new SignupRequest("student21@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150112", Long.valueOf(2)),
                new SignupRequest("student22@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150113", Long.valueOf(2)),
                new SignupRequest("student23@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150114", Long.valueOf(2)),
                new SignupRequest("student24@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150115", Long.valueOf(2)),
                new SignupRequest("student25@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150116", Long.valueOf(2)),
                new SignupRequest("student26@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150117", Long.valueOf(2)),
                new SignupRequest("student27@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150118", Long.valueOf(2)),
                new SignupRequest("student28@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150119", Long.valueOf(2)),
                new SignupRequest("student29@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150120", Long.valueOf(2)),
                new SignupRequest("student30@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150121", Long.valueOf(2)),
                new SignupRequest("student31@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150122", Long.valueOf(2)),
                new SignupRequest("student32@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "BA150123", Long.valueOf(2)),
                new SignupRequest("student33@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150123", Long.valueOf(2)),
                new SignupRequest("student34@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150124", Long.valueOf(1)),
                new SignupRequest("student35@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150125", Long.valueOf(1)),
                new SignupRequest("student36@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150126", Long.valueOf(1)),
                new SignupRequest("student37@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150127", Long.valueOf(1)),
                new SignupRequest("student38@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150128", Long.valueOf(1)),
                new SignupRequest("student39@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150129", Long.valueOf(1)),
                new SignupRequest("student40@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150130", Long.valueOf(1)),
                new SignupRequest("student41@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150131", Long.valueOf(1)),
                new SignupRequest("student42@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150132", Long.valueOf(1)),
                new SignupRequest("student43@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150133", Long.valueOf(1)),
                new SignupRequest("student44@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150134", Long.valueOf(1)),
                new SignupRequest("student45@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150135", Long.valueOf(1)),
                new SignupRequest("student46@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150136", Long.valueOf(1)),
                new SignupRequest("student47@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150137", Long.valueOf(1)),
                new SignupRequest("student48@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150138", Long.valueOf(1)),
                new SignupRequest("student49@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150139", Long.valueOf(1)),
                new SignupRequest("student50@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150140", Long.valueOf(1)),
                new SignupRequest("student51@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.STUDENT.name(), faker.phoneNumber().subscriberNumber(12), faker.address().fullAddress(), "SE150141", Long.valueOf(1)),
                new SignupRequest("representative1@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.COMPANY_REPRESENTATIVE.name(), "1237894560", Long.valueOf(1)),
                new SignupRequest("representative2@gmail.com", initialPassword, faker.name().fullName(), RoleEnum.COMPANY_REPRESENTATIVE.name(), "1237894560", Long.valueOf(2)));

        signupRequests.stream().forEach(signupRequest -> {
            try {
                authController.registerUser(signupRequest);
            } catch (UsernameAlreadyExistedException | EmailAlreadyExistedException | EmptyRoleException | CompanyNotExistedException | MajorNotExistedException e) {
                e.printStackTrace();
            }
        });
    }
}
