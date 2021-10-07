package ojt.management.data.repositories;

import ojt.management.data.entities.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    @Query("SELECT a FROM Major a where a.name = :name")
    List<Major> searchMajor(@Param("name") String name);

    boolean existsByName(String name);

    boolean existsById(Long id);

    Major findByName(String name);
}
