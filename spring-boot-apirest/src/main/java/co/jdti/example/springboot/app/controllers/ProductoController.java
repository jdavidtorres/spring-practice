package co.jdti.example.springboot.app.controllers;

import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService iProductoService;

    @GetMapping
    public Flux<Producto> productoFlux() {
        return iProductoService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> ver(@PathVariable String id) {
        return iProductoService.findById(id).map(producto -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(producto))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Producto>> crear(@RequestBody Producto producto) {
        if (producto.getCreatedAt() == null) {
            producto.setCreatedAt(new Date());
        }
        return iProductoService.save(producto).map(p -> ResponseEntity.created(URI.create("/api/productos/" + p.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> editar(@RequestBody Producto producto, @PathVariable String id) {
        return iProductoService.findById(id).flatMap(p -> {
            p.setNombre(producto.getNombre());
            p.setPrecio(producto.getPrecio());
            p.setCategoria(producto.getCategoria());
            return iProductoService.save(p);
        }).map(p ->
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
        return iProductoService.findById(id).flatMap(p -> iProductoService.delete(p)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }
}
