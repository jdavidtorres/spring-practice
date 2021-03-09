package co.jdti.example.microserviciocursos.app.models.repositories;

import co.jdti.example.microserviciocommons.models.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query("select c from CourseEntity c join fetch c.students a where a.id=?1")
    CourseEntity findCourseByStudentId(Long id);
}
