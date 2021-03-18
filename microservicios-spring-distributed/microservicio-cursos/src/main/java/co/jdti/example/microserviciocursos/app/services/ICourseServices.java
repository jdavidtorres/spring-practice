package co.jdti.example.microserviciocursos.app.services;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.ICommonServices;
import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;

import java.util.List;

public interface ICourseServices extends ICommonServices<CourseEntity> {

    CourseEntity findCourseByStudentId(Long id);

    Iterable<Long> findExamsIdAnsweredByStudent(Long studentId);

    List<StudentEntity> getStudentsByCourse(List<Long> ids);
}
