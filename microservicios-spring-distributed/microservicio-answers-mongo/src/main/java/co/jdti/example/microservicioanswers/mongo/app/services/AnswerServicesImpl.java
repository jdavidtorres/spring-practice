package co.jdti.example.microservicioanswers.mongo.app.services;

import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import co.jdti.example.microservicioanswers.mongo.app.models.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServicesImpl implements IAnswerServices {

    @Autowired
    private IAnswerRepository iAnswerRepository;

    @Override
    public List<AnswerEntity> saveAll(List<AnswerEntity> answers) {
        return iAnswerRepository.saveAll(answers);
    }

    @Override
    public List<AnswerEntity> findAnswerByStudentByExam(Long studentId, Long examId) {
        // Se cambia la implementacion por una consulta sobre MongoDB,
        // queda a eleccion de la implementacion del negocio
        return iAnswerRepository.findAnswerByStudentAndExam(studentId, examId);
    }

    @Override
    public List<Long> findExamsIdsAnsweredByStudent(Long studentId) {
        List<AnswerEntity> answers = iAnswerRepository.findExamsIdsWithAnswerByStudent(studentId);
        return answers.stream()
                .map(answer -> answer.getQuestion().getExam().getId())
                .distinct()
                .collect(Collectors.toList());
    }
}
