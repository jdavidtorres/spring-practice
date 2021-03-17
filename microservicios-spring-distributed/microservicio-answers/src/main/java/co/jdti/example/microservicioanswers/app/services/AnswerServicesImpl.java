package co.jdti.example.microservicioanswers.app.services;

import co.jdti.example.microservicioanswers.app.models.repository.IAnswerRepository;
import co.jdti.example.microserviciocommons.models.entities.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServicesImpl implements IAnswerServices {

    @Autowired
    private IAnswerRepository iAnswerRepository;

    @Override
    @Transactional
    public Iterable<AnswerEntity> saveAll(Iterable<AnswerEntity> answers) {
        return iAnswerRepository.saveAll(answers);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<AnswerEntity> findByStudentByExam(Long studentId, Long examId) {
        return iAnswerRepository.findByStudentByExam(studentId, examId);
    }

    @Override
    public Iterable<Long> findExamsIdAnsweredByStudent(Long studentId) {
        return iAnswerRepository.findExamsIdAnsweredByStudent(studentId);
    }
}
