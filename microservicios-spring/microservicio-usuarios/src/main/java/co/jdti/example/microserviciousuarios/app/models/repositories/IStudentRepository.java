package co.jdti.example.microserviciousuarios.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("select a from StudentEntity a where a.name like %?1% or a.lastname like %?1%")
    List<StudentEntity> findByNameOrLastname(String term);
}
