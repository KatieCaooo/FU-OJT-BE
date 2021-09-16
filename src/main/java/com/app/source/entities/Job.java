package com.app.source.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Job implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    @Column(name = "title", length = 250, nullable = false)
    private String title;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_disabled")
    private boolean disabled;

    //----------[Start]Mapping relationship----------
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "jobs")
    private Set<Major> majors = new HashSet<>();
    //----------[End]Mapping relationship----------

    public Job(String name, String description, String title) {
        this.name = name;
        this.description = description;
        this.title = title;
    }
}
