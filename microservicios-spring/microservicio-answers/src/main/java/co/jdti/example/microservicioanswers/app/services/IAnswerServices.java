package co.jdti.example.microservicioanswers.app.services;

import co.jdti.example.microserviciocommons.models.entities.AnswerEntity;

public interface IAnswerServices {

    Iterable<AnswerEntity> saveAll(Iterable<AnswerEntity> answers);

    Iterable<AnswerEntity> findByStudentByExam(Long studentId, Long examId);

    Iterable<Long> findExamsIdAnsweredByStudent(Long studentId);
}
