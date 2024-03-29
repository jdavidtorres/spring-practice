package co.jdti.example.commons.exam.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exams")
@Data
public class ExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    @Column
    private String name;

    @JsonIgnoreProperties(value = "exam", allowSetters = true)
    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questionsList;

    @JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"}, allowSetters = true)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectEntity subject;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    @Transient
    private boolean answered;

    public ExamEntity() {
        this.questionsList = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        createAt = new Date();
    }

    public void setQuestionsList(List<QuestionEntity> questionsList) {
        this.questionsList.clear();
        questionsList.forEach(this::addQuestion);
    }

    public void addQuestion(QuestionEntity question) {
        this.questionsList.add(question);
        question.setExam(this);
    }

    public void removeQuestion(QuestionEntity question) {
        this.questionsList.remove(question);
        question.setExam(null);
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
