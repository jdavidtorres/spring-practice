package co.jdti.example.microservicioanswers.mongo.app.services;

import co.jdti.example.commons.exam.models.entities.ExamEntity;
import co.jdti.example.commons.exam.models.entities.QuestionEntity;
import co.jdti.example.microservicioanswers.mongo.app.clients.IExamFeignClient;
import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import co.jdti.example.microservicioanswers.mongo.app.models.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServicesImpl implements IAnswerServices {

    @Autowired
    private IAnswerRepository iAnswerRepository;

    @Autowired
    private IExamFeignClient iExamFeignClient;

    @Override
    public List<AnswerEntity> saveAll(List<AnswerEntity> answers) {
        return iAnswerRepository.saveAll(answers);
    }

    @Override
    public List<AnswerEntity> findAnswerByStudentByExam(Long studentId, Long examId) {
        ExamEntity exam = iExamFeignClient.getExamById(examId);
        List<QuestionEntity> questions = exam.getQuestionsList();
        List<Long> questionsIds = questions.stream().map(QuestionEntity::getId).collect(Collectors.toList());
        List<AnswerEntity> answers = iAnswerRepository.findAnswerEntitiesByStudentByQuestionId(studentId, questionsIds);
        answers = answers.stream().map(ans -> {
            questions.forEach(quest -> {
                if (quest.getId() == ans.getQuestionId()) {
                    ans.setQuestion(quest);
                }
            });
            return ans;
        }).collect(Collectors.toList());
        return answers;
    }

    @Override
    public List<Long> findExamsIdAnsweredByStudent(Long studentId) {
        List<AnswerEntity> answers = iAnswerRepository.findByStudentId(studentId);
        List<Long> examsIds = new ArrayList<>();
        if (!answers.isEmpty()) {
            List<Long> answersIds = answers.stream()
                    .map(AnswerEntity::getQuestionId)
                    .collect(Collectors.toList());
            examsIds = iExamFeignClient.findExamsIdsAnsweredByQuestionsIds(answersIds);
        }
        return examsIds;
    }

    @Override
    public List<AnswerEntity> findByStudentId(Long studentId) {
        return iAnswerRepository.findByStudentId(studentId);
    }
}
