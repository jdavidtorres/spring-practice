package co.jdti.example.springboot.app.repositories;

import co.jdti.example.springboot.app.models.documents.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ICategoriaRepository extends ReactiveMongoRepository<Categoria, String> {

    Mono<Categoria> findByNombre(String nombre);
}
