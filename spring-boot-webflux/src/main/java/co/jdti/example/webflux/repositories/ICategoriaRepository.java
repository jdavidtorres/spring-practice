package co.jdti.example.webflux.repositories;

import co.jdti.example.webflux.models.documents.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends ReactiveMongoRepository<Categoria, String> {
}
