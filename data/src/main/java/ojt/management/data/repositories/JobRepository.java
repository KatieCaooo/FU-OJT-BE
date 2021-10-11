package ojt.management.data.repositories;

import ojt.management.data.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    //Search all job (for students)
    @Query("SELECT distinct j " +
            "FROM Job j " +
            "inner join j.semesters s " +
            "inner join j.majors m " +
            "WHERE j.name like :name " +
            "AND j.title like :title " +
            "AND (s.id = :semesterId or :semesterId is null) " +
            "AND (m.id = :majorId or :majorId is null)")
    List<Job> searchJob(@Param("name") String name, @Param("title") String title,
                        @Param("semesterId") Long semesterId, @Param("majorId") Long majorId);

    //Get job by ID for Rep
    @Query("select DISTINCT j FROM Job j inner JOIN j.company c where c.id = :companyId and j.id = :id")
    Job getJobByRep(@Param("companyId") Long companyId, @Param("id") Long id);

    //Search all job (for Rep)
    @Query("SELECT distinct j " +
            "FROM Job j " +
            "inner join j.semesters s " +
            "inner join j.majors m " +
            "inner join j.company c " +
            "WHERE j.name like :name " +
            "AND j.title like :title " +
            "AND (s.id = :semesterId or :semesterId is null) " +
            "AND (m.id = :majorId or :majorId is null) " +
            "AND c.id = :companyId")
    List<Job> searchJobByRep(@Param("name") String name,
                        @Param("title") String title,
                        @Param("semesterId") Long semesterId,
                        @Param("majorId") Long majorId,
                        @Param("companyId") Long companyId);
}
