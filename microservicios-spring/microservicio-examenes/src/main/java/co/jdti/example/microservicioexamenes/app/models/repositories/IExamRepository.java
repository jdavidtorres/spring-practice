package co.jdti.example.microservicioexamenes.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExamRepository extends JpaRepository<ExamEntity, Long> {
}
