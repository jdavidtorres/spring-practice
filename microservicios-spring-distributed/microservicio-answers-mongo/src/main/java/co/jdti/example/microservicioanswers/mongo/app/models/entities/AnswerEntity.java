package co.jdti.example.microservicioanswers.mongo.app.models.entities;

import co.jdti.example.commons.exam.models.entities.QuestionEntity;
import co.jdti.example.commons.student.models.entities.StudentEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "answers")
@Data
public class AnswerEntity {

    @Id
    private String id;

    private String text;

    @Transient
    private StudentEntity student;

    @Transient
    private QuestionEntity question;

    private Long studentId;

    private Long questionId;
}
