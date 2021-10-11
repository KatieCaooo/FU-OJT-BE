package ojt.management.business.services;

import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.payload.request.CompanyUpdateRequest;
import ojt.management.data.entities.Company;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Company> searchCompany(String name, String description) {
        if (name == null && description == null) {
            return companyRepository.findAll();
        } else {
            return companyRepository.searchCompany(name, description);
        }
    }

    @Override
    public Company updateCompany(CompanyUpdateRequest companyUpdateRequest) throws CompanyNotExistedException {
        if (!companyRepository.existsById(companyUpdateRequest.getId())) {
            throw new CompanyNotExistedException();
        }
        Company company = companyRepository.getById(companyUpdateRequest.getId());
        company.setName(companyUpdateRequest.getName());
        company.setDescription(companyUpdateRequest.getDescription());

        return companyRepository.save(company);
    }
}
