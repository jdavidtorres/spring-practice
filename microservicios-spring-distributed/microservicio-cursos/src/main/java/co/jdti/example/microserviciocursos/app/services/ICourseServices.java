package co.jdti.example.microserviciocursos.app.services;

import co.jdti.example.microserviciocommons.services.ICommonServices;
import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;

public interface ICourseServices extends ICommonServices<CourseEntity> {

    CourseEntity findCourseByStudentId(Long id);

    Iterable<Long> findExamsIdAnsweredByStudent(Long studentId);
}
