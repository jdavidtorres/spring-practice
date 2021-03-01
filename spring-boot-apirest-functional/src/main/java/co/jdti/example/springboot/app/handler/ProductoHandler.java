package co.jdti.example.springboot.app.handler;

import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductoHandler {

    @Autowired
    private IProductoService iProductoService;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(iProductoService.findAll(), Producto.class);
    }

    public Mono<ServerResponse> ver(ServerRequest request) {
        String id = request.pathVariable("id");
        return iProductoService.findById(id).flatMap(p -> ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(p))
                .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> crear(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        return productoMono.flatMap(p -> {
            if (p.getCreatedAt() == null) {
                p.setCreatedAt(new Date());
            }
            return iProductoService.save(p);
        }).flatMap(ps -> created(URI.create("/api/v2/productos/".concat(ps.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ps));
    }

    public Mono<ServerResponse> editar(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        String id = request.pathVariable("id");

        Mono<Producto> productoDb = iProductoService.findById(id);

        return productoDb.zipWith(productoMono, (db, req) -> {
            db.setNombre(req.getNombre());
            db.setPrecio(req.getPrecio());
            db.setCategoria(req.getCategoria());
            return db;
        }).flatMap(p -> created(URI.create("/api/v2/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(iProductoService.save(p), Producto.class))
                .switchIfEmpty(noContent().build());
    }
}
