package ojt.management.business.services;

import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.request.CompanyRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.entities.Company;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import ojt.management.data.repositories.RepresentativeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;
    private final RepresentativeRepository representativeRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              AccountRepository accountRepository,
                              RepresentativeRepository representativeRepository) {
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
        this.representativeRepository = representativeRepository;
    }

    @Override
    public Page<Company> searchCompany(Specification<Company> specification, Pageable pageable) {
        return companyRepository.findAll(specification, pageable);
    }

    @Override
    public Company updateCompany(Long id, CompanyRequest companyRequest, Long accountId) throws CrudException {
        if (!companyRepository.existsById(id)) {
            throw new CompanyNotExistedException();
        }
        Company company = companyRepository.getById(id);
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setAddress(companyRequest.getAddress());

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id, Long accountId) throws CrudException {
        Account account = accountRepository.getById(accountId);
        if (account.getRepresentative() != null) { //The Rep only get their company
            Long repCompanyId = representativeRepository.getById(accountId).getCompany().getId();
            if (!id.equals(repCompanyId)) {
                throw new CompanyNotExistedException();
            }
        } else { //The other role can get all
            if (Boolean.FALSE.equals(companyRepository.existsById(id))) {
                throw new CompanyNotExistedException();
            }
        }
        return companyRepository.getById(id);
    }
}
