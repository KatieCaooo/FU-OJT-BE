package ojt.management.data.repositories;

import ojt.management.data.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Company, Long> {
}
