package co.jdti.example.microservicioexamenes.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExamRepository extends JpaRepository<ExamEntity, Long> {

    @Query("select e from ExamEntity e where e.name like %?1%")
    List<ExamEntity>findByName(String term);
}
