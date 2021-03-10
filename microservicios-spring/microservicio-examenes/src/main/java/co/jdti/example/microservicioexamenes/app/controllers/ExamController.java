package co.jdti.example.microservicioexamenes.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microservicioexamenes.app.models.entity.ExamEntity;
import co.jdti.example.microservicioexamenes.app.models.entity.QuestionEntity;
import co.jdti.example.microservicioexamenes.app.services.IExamServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamController extends CommonController<ExamEntity, IExamServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody ExamEntity exam, @PathVariable Long id) {
        Optional<ExamEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        ExamEntity examDb = obj.get();
        List<QuestionEntity> deleted = new ArrayList<>();
        examDb.getQuestionsList().forEach(pdb -> {
            if (!exam.getQuestionsList().contains(pdb)) {
                deleted.add(pdb);
            }
        });
        deleted.forEach(examDb::removeQuestion);

        examDb.setQuestionsList(exam.getQuestionsList());

        return ResponseEntity.status(HttpStatus.CREATED).body(iServices.save(examDb));
    }
}
