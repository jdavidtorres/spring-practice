package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.microserviciousuarios.app.models.entity.StudentEntity;
import co.jdti.example.microserviciousuarios.app.models.repositories.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentServicesImpl implements IStudentServices {

    @Autowired
    private IStudentRepository iStudentRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<StudentEntity> findAll() {
        return iStudentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentEntity> findById(Long id) {
        return iStudentRepository.findById(id);
    }

    @Override
    @Transactional
    public StudentEntity save(StudentEntity newStudent) {
        return iStudentRepository.save(newStudent);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        iStudentRepository.deleteById(id);
    }
}
