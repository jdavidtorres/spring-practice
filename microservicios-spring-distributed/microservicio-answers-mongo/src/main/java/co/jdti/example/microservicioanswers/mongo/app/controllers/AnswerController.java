package co.jdti.example.microservicioanswers.mongo.app.controllers;

import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import co.jdti.example.microservicioanswers.mongo.app.services.IAnswerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnswerController {

    @Autowired
    private IAnswerServices iAnswerServices;

    @PostMapping
    public ResponseEntity<?> saveAll(@RequestBody List<AnswerEntity> answers) {
        answers = answers.stream()
                .map(r -> {
                    r.setStudentId(r.getStudent().getId());
                    r.setQuestionId(r.getQuestion().getId());
                    return r;
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(iAnswerServices.saveAll(answers));
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<?> findByStudentByExam(@PathVariable Long studentId, @PathVariable Long examId) {
        return ResponseEntity.ok(iAnswerServices.findAnswerByStudentByExam(studentId, examId));
    }

    @GetMapping("/student/{studentId}/answered-exam")
    public ResponseEntity<?> findExamsIdAnsweredByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(iAnswerServices.findExamsIdsAnsweredByStudent(studentId));
    }
}
