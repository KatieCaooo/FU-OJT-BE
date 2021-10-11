package ojt.management.data.repositories;

import ojt.management.data.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Company, Long> {
}
