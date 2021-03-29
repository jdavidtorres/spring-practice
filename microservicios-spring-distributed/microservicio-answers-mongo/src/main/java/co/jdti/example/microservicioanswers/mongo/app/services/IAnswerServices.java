package co.jdti.example.microservicioanswers.mongo.app.services;


import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;

import java.util.List;

public interface IAnswerServices {

    List<AnswerEntity> saveAll(List<AnswerEntity> answers);

    List<AnswerEntity> findAnswerByStudentByExam(Long studentId, Long examId);

    List<Long> findExamsIdsAnsweredByStudent(Long studentId);
}
