package ojt.management.business.services;

import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.common.payload.request.CompanyUpdateRequest;
import ojt.management.data.entities.Company;

import java.util.List;

public interface CompanyService {
    List<Company> searchCompany(String name, String description);

    Company updateCompany(CompanyUpdateRequest companyUpdateRequest) throws CompanyNotExistedException;
}
