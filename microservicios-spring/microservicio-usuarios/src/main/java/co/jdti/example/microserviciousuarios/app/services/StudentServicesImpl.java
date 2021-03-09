package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.CommonServicesImpl;
import co.jdti.example.microserviciousuarios.app.models.repositories.IStudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServicesImpl extends CommonServicesImpl<StudentEntity, IStudentRepository> implements IStudentServices {

    @Override
    @Transactional(readOnly = true)
    public List<StudentEntity> findByNameOrLastname(String term) {
        return iRepository.findByNameOrLastname(term);
    }
}
