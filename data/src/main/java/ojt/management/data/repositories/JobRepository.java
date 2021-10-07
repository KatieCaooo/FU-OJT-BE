package ojt.management.data.repositories;
import ojt.management.data.entities.Job;
import ojt.management.data.entities.Major;
import ojt.management.data.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

    @Query("SELECT a FROM Job a where a.name = :name or a.description = :description or a.title = :title or a.semesters = :semesters or a.majors = :majors")
    List<Job> searchJob(@Param("name") String name, @Param("description") String description, @Param("title") String title,
                        @Param("semesters") String semesters, @Param("majors") String majors);

    boolean existsByCompany(Long id);

    boolean existsByName(String name);

}
