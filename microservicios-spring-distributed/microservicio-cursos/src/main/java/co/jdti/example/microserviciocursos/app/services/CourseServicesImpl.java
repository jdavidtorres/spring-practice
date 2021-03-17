package co.jdti.example.microserviciocursos.app.services;

import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microserviciocursos.app.clients.IAnswerFeignClient;
import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;
import co.jdti.example.microserviciocursos.app.models.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServicesImpl extends CommonServicesImpl<CourseEntity, ICourseRepository> implements ICourseServices {

    @Autowired
    IAnswerFeignClient iAnswerFeignClient;

    @Override
    @Transactional(readOnly = true)
    public CourseEntity findCourseByStudentId(Long id) {
        return iRepository.findCourseByStudentId(id);
    }

    @Override
    public Iterable<Long> findExamsIdAnsweredByStudent(Long studentId) {
        return iAnswerFeignClient.findExamsIdAnsweredByStudent(studentId);
    }
}
