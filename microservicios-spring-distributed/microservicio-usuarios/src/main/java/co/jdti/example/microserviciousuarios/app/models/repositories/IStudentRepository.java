package co.jdti.example.microserviciousuarios.app.models.repositories;

import co.jdti.example.commons.student.models.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("select s from StudentEntity s where upper(s.name) like %?1% or upper(s.lastname) like %?1%")
    List<StudentEntity> findByNameOrLastname(String term);
}
