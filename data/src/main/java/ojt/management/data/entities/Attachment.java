package ojt.management.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "attachment")
@EntityListeners(AuditingEntityListener.class)
public class Attachment {
    @Id
    @Column(name = "key")
    private String key;

    @Column(name = "name")
    @NotNull
    private String name;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne
    private Application application;
}
