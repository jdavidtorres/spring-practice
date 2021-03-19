package co.jdti.example.microservicioanswers.app.models.repository;

import co.jdti.example.microservicioanswers.app.models.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Query("select a from AnswerEntity a join fetch a.question q join fetch q.exam e where a.studentId=?1 and e.id=?2")
    List<AnswerEntity> findByStudentByExam(Long studentId, Long examId);

    @Query("select e.id from AnswerEntity a join a.question q join q.exam e where a.studentId=?1 group by e.id")
    List<Long> findExamsIdAnsweredByStudent(Long studentId);
}
