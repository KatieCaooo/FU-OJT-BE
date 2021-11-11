package ojt.management.data.repositories;

import ojt.management.data.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    //Get job by ID for Rep
    @Query("SELECT DISTINCT j " +
            "FROM Job j " +
            "INNER JOIN j.company c " +
            "WHERE c.id = :companyId " +
            "AND j.id = :id")
    Job getJobByRep(@Param("companyId") Long companyId, @Param("id") Long id);
}
