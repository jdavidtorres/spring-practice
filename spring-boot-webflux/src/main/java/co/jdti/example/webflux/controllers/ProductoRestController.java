package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private IProductRepository iProductRepository;

    @GetMapping
    public Flux<Producto> getProductos() {
        return iProductRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
    }

    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id) {
        return iProductRepository.findById(id);
    }
}
