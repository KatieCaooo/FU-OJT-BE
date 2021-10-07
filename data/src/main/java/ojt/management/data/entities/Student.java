package ojt.management.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address", length = 512)
    private String address;

    @Column(name = "student_code", length = 10, nullable = false)
    private String studentCode;

    //----------[Start]Mapping relationship----------
    @OneToOne
    private Account account;

    @ManyToOne
    private Major major;

    @ManyToOne
    private Semester semester;

    @OneToMany(mappedBy = "student")
    private Set<Application> applications;
    //----------[End]Mapping relationship----------

}
