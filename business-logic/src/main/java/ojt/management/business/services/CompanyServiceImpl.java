package ojt.management.business.services;

import ojt.management.common.exceptions.CompanyNameAlreadyExistedException;
import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.payload.request.LoginRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.entities.Company;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public CompanyServiceImpl (CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Company> searchCompany(String name, String description) {
        if (name == null & description == null) {
            return companyRepository.findAll();
        } else {
            return companyRepository.searchCompany(name, description);
        }
    }

    @Override
    public Company updateCompany(String name, String description) throws CompanyNotExistedException, CompanyNameAlreadyExistedException {
        if (Boolean.TRUE.equals(companyRepository.existsByName(name))) {
            throw new CompanyNameAlreadyExistedException();
        }
        LoginRequest loginRequest = new LoginRequest();
        Account account = accountRepository.findByEmail(loginRequest.getEmail());
        Company company = account.getRepresentative().getCompany();
        if (company == null) {
            throw new CompanyNotExistedException();
        } else {
            if (name != null)
                company.setName(name);
            if (description != null)
                company.setDescription(description);
            companyRepository.save(company);
            return company;
        }
    }
}
