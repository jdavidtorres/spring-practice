package co.jdti.example.microservicioexamenes.app.models.repositories;

import co.jdti.example.commons.exam.models.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubjectRepository extends JpaRepository<SubjectEntity, Long> {
}
