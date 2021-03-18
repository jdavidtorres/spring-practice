package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import co.jdti.example.microserviciocommons.services.ICommonServices;

import java.util.List;
import java.util.Optional;

public interface IStudentServices extends ICommonServices<StudentEntity> {

    List<StudentEntity> findAll();

    Optional<StudentEntity> findById(Long id);

    StudentEntity save(StudentEntity newStudent);

    void deleteById(Long id);

    List<StudentEntity> findByNameOrLastname(String term);

    List<StudentEntity> findAllByIds(List<Long> ids);
}
