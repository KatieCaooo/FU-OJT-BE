package ojt.management.data.repositories;

import ojt.management.data.entities.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {

    boolean existsByName(String name);

    boolean existsById(Long id);

    List<Major> findByName(String name);

    List<Major> findAllByIdIn(List<Long> ids);
}
