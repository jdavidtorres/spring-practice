package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.commons.student.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microserviciousuarios.app.client.ICourseFeignClient;
import co.jdti.example.microserviciousuarios.app.models.repositories.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServicesImpl extends CommonServicesImpl<StudentEntity, IStudentRepository> implements IStudentServices {

    @Autowired
    private ICourseFeignClient iCourseFeignClient;

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findByNameOrLastname(String term) {
        return iRepository.findByNameOrLastname(term.toUpperCase());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findAllByIds(List<Long> ids) {
        return iRepository.findAllById(ids);
    }

    @Override
    public void deleteCourseStudentById(Long id) {
        iCourseFeignClient.deleteCourseStudentById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        deleteCourseStudentById(id);
    }
}
