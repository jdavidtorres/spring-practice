package co.jdti.example.microserviciocursos.app.services;

import co.jdti.example.commons.student.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microserviciocursos.app.clients.IAnswerFeignClient;
import co.jdti.example.microserviciocursos.app.clients.IStudentFeignClient;
import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;
import co.jdti.example.microserviciocursos.app.models.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServicesImpl extends CommonServicesImpl<CourseEntity, ICourseRepository> implements ICourseServices {

    @Autowired
    IAnswerFeignClient iAnswerFeignClient;

    @Autowired
    IStudentFeignClient iStudentFeignClient;

    @Override
    @Transactional(readOnly = true)
    public CourseEntity findCourseByStudentId(Long id) {
        return iRepository.findCourseByStudentId(id);
    }

    @Override
    public List<Long> findExamsIdAnsweredByStudent(Long studentId) {
        return iAnswerFeignClient.findExamsIdAnsweredByStudent(studentId);
    }

    @Override
    public List<StudentEntity> getStudentsByCourse(List<Long> ids) {
        return iStudentFeignClient.getStudentsByCourse(ids);
    }

    @Override
    @Transactional
    public void deleteCourseStudentById(Long id) {
        iRepository.deleteCourseStudentById(id);
    }
}
