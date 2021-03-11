package co.jdti.example.microserviciocursos.app.controllers;

import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocommons.models.entities.CourseEntity;
import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciocursos.app.services.ICourseServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController extends CommonController<CourseEntity, ICourseServices> {

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody CourseEntity courseEntity, @PathVariable Long id) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseEntity.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(courseEntity));
    }

    @PutMapping("{id}/assign-students")
    public ResponseEntity<?> assignStudents(@PathVariable Long id, @RequestBody List<StudentEntity> students) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        students.forEach(course::addStudent);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }

    @PutMapping("{id}/delete-student")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id, @RequestBody StudentEntity student) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        course.removeStudent(student);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> findByStudentId(@PathVariable Long id) {
        CourseEntity courseE = iServices.findCourseByStudentId(id);
        if (courseE == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courseE);
    }

    @PutMapping("{id}/assign-exams")
    public ResponseEntity<?> assignExam(@PathVariable Long id, @RequestBody List<ExamEntity> exams) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        exams.forEach(course::addExam);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }

    @PutMapping("{id}/delete-exam")
    public ResponseEntity<?> deleteExam(@PathVariable Long id, @RequestBody ExamEntity exam) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        course.removeExam(exam);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }
}
