package co.jdti.example.microservicioanswers.app.services;

import co.jdti.example.microservicioanswers.app.models.entities.AnswerEntity;

import java.util.List;

public interface IAnswerServices {

    List<AnswerEntity> saveAll(List<AnswerEntity> answers);

    List<AnswerEntity> findByStudentByExam(Long studentId, Long examId);

    List<Long> findExamsIdAnsweredByStudent(Long studentId);
}
