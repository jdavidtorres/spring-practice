package co.jdti.example.springboot.app.controllers;

import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService iProductoService;

    @Value("${config.uploads.path}")
    private String path;

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

    @PostMapping("/upload/{id}")
    public Mono<ResponseEntity<Producto>> upload(@PathVariable String id, @RequestPart FilePart filePart) {
        return iProductoService.findById(id).flatMap(p -> {
            p.setFoto(UUID.randomUUID().toString() + "_" + filePart.filename()
                    .replace(" ", "")
                    .replace(":", "")
                    .replace("\\", ""));
            return filePart.transferTo(new File(path + p.getFoto())).then(iProductoService.save(p));
        }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping("/v2")
    public Mono<ResponseEntity<Producto>> crearConFoto(Producto producto, @RequestPart FilePart filePart) {
        if (producto.getCreatedAt() == null) {
            producto.setCreatedAt(new Date());
        }
        producto.setFoto(UUID.randomUUID().toString() + "_" + filePart.filename()
                .replace(" ", "")
                .replace(":", "")
                .replace("\\", ""));

        return filePart.transferTo(new File(path + producto.getFoto()))
                .then(iProductoService.save(producto))
                .map(p -> ResponseEntity.created(URI.create("/api/productos/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.badRequest().body(producto));
    }

    @PostMapping("/crear")
    public Mono<ResponseEntity<Map<String, Object>>> crearValid(@Valid @RequestBody Mono<Producto> productoMono) {
        Map<String, Object> respuesta = new HashMap<>();

        return productoMono.flatMap(producto -> {
            if (producto.getCreatedAt() == null) {
                producto.setCreatedAt(new Date());
            }
            return iProductoService.save(producto).map(p -> {
                respuesta.put("producto", p);
                return ResponseEntity.created(URI.create("/api/productos/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(respuesta);
            });
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });
        });
    }
}
