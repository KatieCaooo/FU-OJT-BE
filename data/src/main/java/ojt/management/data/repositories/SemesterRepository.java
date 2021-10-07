package ojt.management.data.repositories;

import ojt.management.data.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SemesterRepository  extends JpaRepository<Semester, Long> {

    @Query("SELECT a FROM Semester a where a.name = :name or a.startDate = :startDate or a.endDate = :endDate")
    List<Semester> searchSemester(@Param("name") String name, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    boolean existsByName(String name);

    boolean existsByStartDateAndEndDate(Date startDate, Date endDate);

    boolean existsById(Long id);

    Semester findByName(String name);
}
