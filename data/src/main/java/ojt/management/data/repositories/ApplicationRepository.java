package ojt.management.data.repositories;

import ojt.management.data.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    @Query("SELECT a " +
            "FROM Application a " +
            "WHERE a.id = :id ")
    Application getApp(@Param("id") Long id);

}
