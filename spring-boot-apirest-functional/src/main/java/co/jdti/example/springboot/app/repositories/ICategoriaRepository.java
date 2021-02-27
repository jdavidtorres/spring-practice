package co.jdti.example.springboot.app.repositories;

import co.jdti.example.springboot.app.models.documents.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends ReactiveMongoRepository<Categoria, String> {
}
