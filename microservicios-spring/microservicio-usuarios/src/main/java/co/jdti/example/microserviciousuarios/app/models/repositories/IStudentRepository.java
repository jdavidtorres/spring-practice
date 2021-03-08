package co.jdti.example.microserviciousuarios.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {
}
