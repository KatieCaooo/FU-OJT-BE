package ojt.management.mappers;
import ojt.management.common.payload.dto.CompanyDTO;
import ojt.management.data.entities.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDTO companyToCompanyDTO (Company company);

    Company companyDTOToCompany (CompanyDTO companyDTO);
}
