package co.jdti.example.microservicioanswers.mongo.app.services;

import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import co.jdti.example.microservicioanswers.mongo.app.models.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerServicesImpl implements IAnswerServices {

    @Autowired
    private IAnswerRepository iAnswerRepository;

    @Override
    @Transactional
    public List<AnswerEntity> saveAll(List<AnswerEntity> answers) {
        return iAnswerRepository.saveAll(answers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerEntity> findByStudentByExam(Long studentId, Long examId) {
        return null;// iAnswerRepository.findByStudentByExam(studentId, examId);
    }

    @Override
    public List<Long> findExamsIdAnsweredByStudent(Long studentId) {
        return null;// iAnswerRepository.findExamsIdAnsweredByStudent(studentId);
    }
}
