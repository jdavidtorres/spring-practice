package co.jdti.example.springboot.app.services;

import co.jdti.example.springboot.app.models.documents.Categoria;
import co.jdti.example.springboot.app.models.documents.Producto;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoService {

    Flux<Producto> findAll();

    Flux<Producto> findAllConNombreUpperCase();

    Flux<Producto> findAllConNombreUpperCaseRepeat();

    Mono<Producto> findById(String id);

    Mono<Producto> save(Producto producto);

    Mono<Void> delete(Producto producto);

    Flux<Categoria> findAllCategoria();

    Mono<Categoria> findCategoriaById(String id);

    Mono<Categoria> save(Categoria categoria);

    Mono<Producto> findByNombre(String nombre);

    @Query("{ 'nombre' : ?0 }")
    Mono<Producto> obtenerPorNombre(String nombre);
}
