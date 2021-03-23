package co.jdti.example.microservicioanswers.mongo.app.models.repository;

import co.jdti.example.microservicioanswers.mongo.app.models.entities.AnswerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepository extends MongoRepository<AnswerEntity, String> {

}
