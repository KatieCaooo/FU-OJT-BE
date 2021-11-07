package ojt.management.data.repositories;

import ojt.management.data.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query("select a " +
            "FROM Attachment a " +
            "where a.key = :key")
    Attachment getAttachmentByKey(@Param("Key") String key);

    boolean existsAttachmentByApplicationIdOrKey(Long applicationId, String key);
}
