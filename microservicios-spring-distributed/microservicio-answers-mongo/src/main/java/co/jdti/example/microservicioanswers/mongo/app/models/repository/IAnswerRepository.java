package co.jdti.example.microservicioanswers.mongo.app.models.repository;

import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAnswerRepository extends MongoRepository<AnswerEntity, String> {

    @Query("{'studentId' : ?0, 'questionId':{$in:?1}}")
    List<AnswerEntity> findAnswerEntitiesByStudentByQuestionId(Long studentId, List<Long> questionIds);

    @Query("{'studentId' : ?0}")
    List<AnswerEntity> findByStudentId(Long studentId);
}
