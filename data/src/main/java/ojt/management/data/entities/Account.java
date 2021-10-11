package ojt.management.data.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email(message = "Email is not valid")
    @Column(name = "email", length = 320, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 1024, nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", length = 13, nullable = false)
    private String phone;

    @Column(name = "is_admin")
    private boolean admin;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_disabled")
    private boolean isDisabled;

    //----------[Start]Mapping relationship----------
    @OneToOne(mappedBy = "account")
    private Student student;

    @OneToOne(mappedBy = "account")
    private Representative representative;
    //----------[End]Mapping relationship----------

    public Account(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
}
