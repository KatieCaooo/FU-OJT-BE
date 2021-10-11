package ojt.management.data.repositories;

import ojt.management.data.entities.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c " +
            "FROM Company c " +
            "WHERE c.name like :name " +
            "OR c.description like :description")
    List<Company> searchCompany(@Param("name") String name, @Param("description") String description);

    boolean existsById(Long id);

}
