package co.jdti.example.microserviciocursos.app.models.entities;

import co.jdti.example.commons.exam.models.entities.ExamEntity;
import co.jdti.example.commons.student.models.entities.StudentEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Column
    private String name;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @JsonIgnoreProperties(value = "course", allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseStudentEntity> courseStudentList;

    @Transient
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ExamEntity> exams;

    public CourseEntity() {
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.courseStudentList = new ArrayList<>();
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

    public void addExam(ExamEntity exam) {
        this.exams.add(exam);
    }

    public void removeExam(ExamEntity exam) {
        this.exams.remove(exam);
    }

    public void addCourseStudent(CourseStudentEntity courseStudent) {
        this.courseStudentList.add(courseStudent);
    }

    public void removeCourseStudent(CourseStudentEntity courseStudent) {
        this.courseStudentList.remove(courseStudent);
    }
}
