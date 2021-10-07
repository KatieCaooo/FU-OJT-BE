package ojt.management.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Application implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "experience", length = 1000)
    private String experience;

    @OneToMany(mappedBy = "application")
    private Set<Attachment> attachments;

    @Column(name = "is_company_accepted")
    private boolean isCompanyAccepted;

    @Column(name = "accepted_at")
    private Timestamp acceptedAt;

    @Column(name = "is_student_comfirmed")
    private boolean isStudentComfirmed;

    @Column(name = "confirmed_at")
    private Timestamp confirmedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_disabled")
    private boolean disabled;

    //----------[Start]Mapping relationship----------

    @OneToOne
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    //----------[End]Mapping relationship----------

    public Application(String experience, Set<Attachment> attachments) {
        this.experience = experience;
        this.attachments = attachments;
    }

}
