package ojt.management.data.repositories;

import ojt.management.data.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentativeRepository extends JpaRepository<Representative, Long> {
}
