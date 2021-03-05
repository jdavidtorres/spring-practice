package co.jdti.example.microserviciousuarios.app.controllers;

import co.jdti.example.microserviciousuarios.app.models.entity.StudentEntity;
import co.jdti.example.microserviciousuarios.app.services.IStudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private IStudentServices iStudentServices;

    @GetMapping
    public ResponseEntity<Iterable<StudentEntity>> listAll() {
        return ResponseEntity.ok().body(iStudentServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<StudentEntity> obj = iStudentServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StudentEntity studentEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iStudentServices.save(studentEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody StudentEntity studentEntity, @PathVariable Long id) {
        Optional<StudentEntity> obj = iStudentServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        studentEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iStudentServices.save(studentEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        iStudentServices.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
