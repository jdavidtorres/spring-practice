package co.jdti.example.springboot.app.repositories;

import co.jdti.example.springboot.app.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Producto, String> {
}
