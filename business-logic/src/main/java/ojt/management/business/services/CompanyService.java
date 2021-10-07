package ojt.management.business.services;

import ojt.management.common.exceptions.CompanyNameAlreadyExistedException;
import ojt.management.common.exceptions.CompanyNotExistedException;
import ojt.management.data.entities.Company;

import java.util.List;

public interface CompanyService {
    List<Company> searchCompany(String name, String description);

    Company updateCompany(String name, String description) throws CompanyNotExistedException, CompanyNameAlreadyExistedException;
}
