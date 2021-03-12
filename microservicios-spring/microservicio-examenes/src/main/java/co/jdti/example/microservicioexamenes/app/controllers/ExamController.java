package co.jdti.example.microservicioexamenes.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import co.jdti.example.microservicioexamenes.app.services.IExamServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ExamController extends CommonController<ExamEntity, IExamServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody ExamEntity exam, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        Optional<ExamEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        ExamEntity examDb = obj.get();
        examDb.setName(exam.getName());

        examDb.setQuestionsList(exam.getQuestionsList());

        return ResponseEntity.status(HttpStatus.CREATED).body(iServices.save(examDb));
    }

    @GetMapping("/filter/{term}")
    public ResponseEntity<?> filter(@PathVariable String term) {
        return ResponseEntity.ok(iServices.findByName(term));
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> findAllSubjects() {
        return ResponseEntity.ok(iServices.findAllSubjects());
    }
}
