package co.jdti.example.webflux.repositories;

import co.jdti.example.webflux.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Producto, String> {
}
