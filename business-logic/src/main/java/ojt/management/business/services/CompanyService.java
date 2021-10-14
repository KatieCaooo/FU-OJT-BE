package ojt.management.business.services;

import ojt.management.common.exceptions.CrudException;
import ojt.management.common.payload.request.CompanyCreateRequest;
import ojt.management.common.payload.request.CompanyUpdateRequest;
import ojt.management.data.entities.Company;

import java.util.List;

public interface CompanyService {
    List<Company> searchCompany(String name, String description);

    Company updateCompany(CompanyUpdateRequest companyUpdateRequest) throws CrudException;

    Company getCompanyById(Long id, Long accountId) throws CrudException;

    Company createCompany(CompanyCreateRequest companyCreateRequest);

}
