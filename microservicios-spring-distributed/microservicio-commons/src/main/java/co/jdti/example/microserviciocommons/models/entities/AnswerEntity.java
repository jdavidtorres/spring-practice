package co.jdti.example.microserviciocommons.models.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "answers")
@Data
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    private String text;

    @Transient
    private StudentEntity student;

    @OneToOne(fetch = FetchType.LAZY)
    private QuestionEntity question;

    @Column(name = "student_id", unique = true)
    private Long studentId;
}
