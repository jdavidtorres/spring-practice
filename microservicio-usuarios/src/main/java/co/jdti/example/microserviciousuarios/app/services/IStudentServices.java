package co.jdti.example.microserviciousuarios.app.services;

import co.jdti.example.microserviciousuarios.app.models.entity.StudentEntity;

import java.util.Optional;

public interface IStudentServices {

    Iterable<StudentEntity> findAll();

    Optional<StudentEntity> findById(Long id);

    StudentEntity save(StudentEntity newStudent);

    void deleteById(Long id);
}
