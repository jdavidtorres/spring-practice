package co.jdti.example.microservicioanswers.app.controllers;

import co.jdti.example.microservicioanswers.app.services.IAnswerServices;
import co.jdti.example.microserviciocommons.models.entities.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    private IAnswerServices iAnswerServices;

    @PostMapping
    public ResponseEntity<?> saveAll(@RequestBody Iterable<AnswerEntity> answers) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iAnswerServices.saveAll(answers));
    }

    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<?> findByStudentByExam(@PathVariable Long studentId, @PathVariable Long examId) {
        return ResponseEntity.ok(iAnswerServices.findByStudentByExam(studentId, examId));
    }

    @GetMapping("/student/{studentId}/answered-exam")
    public ResponseEntity<?> findExamsIdAnsweredByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(iAnswerServices.findExamsIdAnsweredByStudent(studentId));
    }
}
