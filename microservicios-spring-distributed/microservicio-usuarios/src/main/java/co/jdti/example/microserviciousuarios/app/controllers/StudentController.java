package co.jdti.example.microserviciousuarios.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciousuarios.app.services.IStudentServices;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController extends CommonController<StudentEntity, IStudentServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody StudentEntity studentEntity, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        Optional<StudentEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        studentEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(studentEntity));
    }

    @GetMapping("/filter/{term}")
    public ResponseEntity<?> filterByNameOrLastname(@PathVariable String term) {
        return ResponseEntity.ok(iServices.findByNameOrLastname(term));
    }

    @PostMapping("/save-photo")
    public ResponseEntity<?> saveWithPhoto(@Valid StudentEntity student, BindingResult result, @RequestParam MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            student.setPhoto(file.getBytes());
        }
        return super.save(student, result);
    }

    @PutMapping("/edit-photo/{id}")
    public ResponseEntity<?> editWithPhoto(@Valid StudentEntity student, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        Optional<StudentEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        student.setId(id);
        if (!file.isEmpty()) {
            student.setPhoto(file.getBytes());
        }
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(student));
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable Long id) {
        Optional<StudentEntity> obj = iServices.findById(id);
        if (obj.isEmpty() || obj.get().getPhoto() == null) {
            return ResponseEntity.noContent().build();
        }
        Resource img = new ByteArrayResource(obj.get().getPhoto());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(img);
    }

    @GetMapping("/students-course")
    public ResponseEntity<?> getStudentsByCourse(@RequestParam List<Long> ids) {
        return ResponseEntity.ok()
                .body(iServices.findAllByIds(ids));
    }
}
