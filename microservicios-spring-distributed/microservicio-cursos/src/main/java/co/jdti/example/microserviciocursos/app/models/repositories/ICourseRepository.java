package co.jdti.example.microserviciocursos.app.models.repositories;

import co.jdti.example.microserviciocursos.app.models.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query("select c from CourseEntity c join fetch c.courseStudentList cs where cs.studentId=?1")
    CourseEntity findCourseByStudentId(Long id);

    @Modifying
    @Query("delete from CourseStudentEntity cs where cs.studentId=?1")
    void deleteCourseStudentById(Long id);
}
