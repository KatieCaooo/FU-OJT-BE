package ojt.management.business.services;

import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.request.CompanyRequest;
import ojt.management.data.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CompanyService {
    Page<Company> searchCompany(Specification<Company> specification, Pageable pageable);

    Company updateCompany(Long id, CompanyRequest companyRequest, Long accountId) throws CrudException;

    Company getCompanyById(Long id, Long accountId) throws CrudException;
}
