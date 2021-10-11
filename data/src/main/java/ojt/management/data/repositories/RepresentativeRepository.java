package ojt.management.data.repositories;

import ojt.management.data.entities.Account;
import ojt.management.data.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

}
