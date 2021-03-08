package co.jdti.example.microserviciocommons.models.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "courses")
@Data
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<StudentEntity> students;

    public CourseEntity() {
        this.students = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        this.createAt = new Date();
    }

    public void addStudent(StudentEntity student) {
        this.students.add(student);
    }

    public void removeStudent(StudentEntity student) {
        this.students.remove(student);
    }
}
