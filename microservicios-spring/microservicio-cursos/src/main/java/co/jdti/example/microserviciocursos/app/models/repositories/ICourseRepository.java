package co.jdti.example.microserviciocursos.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {

}
