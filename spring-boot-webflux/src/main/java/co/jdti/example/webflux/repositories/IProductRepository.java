package co.jdti.example.webflux.repositories;

import co.jdti.example.webflux.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IProductRepository extends ReactiveMongoRepository<Producto, String> {
}
