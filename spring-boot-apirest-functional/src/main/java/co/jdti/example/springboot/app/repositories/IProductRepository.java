package co.jdti.example.springboot.app.repositories;

import co.jdti.example.springboot.app.models.documents.Producto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Producto, String> {

    Mono<Producto> findByNombre(String nombre);

    @Query("{ 'nombre' : ?0 }")
    Mono<Producto> obtenerPorNombre(String nombre);
}
