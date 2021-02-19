package co.jdti.example.webflux.services;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Flux<Producto> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCase() {
        return iProductRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCaseRepeat() {
        return findAllConNombreUpperCase().repeat(5000);
    }

    @Override
    public Mono<Producto> findById(String id) {
        return iProductRepository.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return iProductRepository.save(producto);
    }

    @Override
    public Mono<Void> delete(Producto producto) {
        return iProductRepository.delete(producto);
    }
}
