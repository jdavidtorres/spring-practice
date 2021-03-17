package co.jdti.example.microservicioanswers.app.models.repository;

import co.jdti.example.microserviciocommons.models.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Query("select a from AnswerEntity a join fetch a.student s join fetch a.question q join fetch q.exam e where s.id=?1 and e.id=?2")
    Iterable<AnswerEntity> findByStudentByExam(Long studentId, Long examId);

    @Query("select e.id from AnswerEntity a join a.student s join a.question q join q.exam e where s.id=?1 group by e.id")
    Iterable<Long> findExamsIdAnsweredByStudent(Long studentId);
}
