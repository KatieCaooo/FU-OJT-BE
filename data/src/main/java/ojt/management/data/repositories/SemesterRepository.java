package ojt.management.data.repositories;

import ojt.management.data.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

    boolean existsByName(String name);

    boolean existsByStartDateAndEndDate(Date startDate, Date endDate);

    boolean existsById(Long id);

    List<Semester> findAllByIdIn(List<Long> ids);
}
