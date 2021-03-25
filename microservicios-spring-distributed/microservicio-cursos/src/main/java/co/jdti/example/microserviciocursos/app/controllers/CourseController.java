package co.jdti.example.microserviciocursos.app.controllers;

import co.jdti.example.commons.exam.models.entities.ExamEntity;
import co.jdti.example.commons.student.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.controllers.CommonController;
import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;
import co.jdti.example.microserviciocursos.app.models.entities.CourseStudentEntity;
import co.jdti.example.microserviciocursos.app.services.ICourseServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CourseController extends CommonController<CourseEntity, ICourseServices> {

    @Value("${config.balancer.test}")
    private String balancer;

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody CourseEntity courseEntity, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
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
        students.forEach(student -> {
            CourseStudentEntity courseStudent = new CourseStudentEntity();
            courseStudent.setStudentId(student.getId());
            courseStudent.setCourse(course);
            course.addCourseStudent(courseStudent);
        });
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }

    @PutMapping("{id}/delete-student")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id, @RequestBody StudentEntity student) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        CourseStudentEntity courseStudent = new CourseStudentEntity();
        courseStudent.setStudentId(student.getId());
        course.removeCourseStudent(courseStudent);
        return ResponseEntity.status(HttpStatus.OK).body(iServices.save(course));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> findByStudentId(@PathVariable Long id) {
        CourseEntity courseE = iServices.findCourseByStudentId(id);
        if (courseE == null) {
            return ResponseEntity.noContent().build();
        } else {
            List<Long> examsIds = iServices.findExamsIdAnsweredByStudent(id);
            if (examsIds != null && !examsIds.isEmpty()) {
                List<ExamEntity> exams = courseE.getExams().stream().map(exam -> {
                    if (examsIds.contains(exam.getId())) {
                        exam.setAnswered(true);
                    }
                    return exam;
                }).collect(Collectors.toList());
                courseE.setExams(exams);
            }
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

    @GetMapping("/balancer")
    public ResponseEntity<?> listAllWithBalancer() {
        Map<String, Object> response = new HashMap<>();
        response.put("balancer", balancer);
        response.put("courses", iServices.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> listAll() {
        List<CourseEntity> courses = iServices.findAll().stream()
                .map(course -> {
                    course.getCourseStudentList().forEach(
                            courseStudent -> {
                                StudentEntity student = new StudentEntity();
                                student.setId(courseStudent.getId());
                                course.addStudent(student);
                            }
                    );
                    return course;
                }).collect(Collectors.toList());
        return ResponseEntity.ok().body(courses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CourseEntity course = obj.get();
        if (!course.getCourseStudentList().isEmpty()) {
            List<Long> ids = course.getCourseStudentList().stream()
                    .map(CourseStudentEntity::getStudentId)
                    .collect(Collectors.toList());
            course.setStudents(iServices.getStudentsByCourse(ids));
        }
        return ResponseEntity.ok().body(course);
    }

    @Override
    @GetMapping("/pageable")
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<CourseEntity> courses = iServices.findAll(pageable)
                .map(course -> {
                    course.getCourseStudentList().forEach(
                            courseStudent -> {
                                StudentEntity student = new StudentEntity();
                                student.setId(courseStudent.getId());
                                course.addStudent(student);
                            }
                    );
                    return course;
                });
        return ResponseEntity.ok().body(courses);
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<?> deleteCourseStudentById(@PathVariable Long id) {
        Optional<CourseEntity> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        iServices.deleteCourseStudentById(id);
        return ResponseEntity.ok().build();
    }
}
