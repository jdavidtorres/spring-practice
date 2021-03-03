package co.jdti.example.client.app.handler;

import co.jdti.example.client.app.models.Producto;
import co.jdti.example.client.app.services.IProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductoHandler {

    @Autowired
    private IProductoServices iProductoServices;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(iProductoServices.findAll(), Producto.class);
    }

    public Mono<ServerResponse> ver(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(APPLICATION_JSON)
                .body(iProductoServices.findById(id), Producto.class);
    }

    public Mono<ServerResponse> crear(ServerRequest request) {
        Mono<Producto> producto = request.bodyToMono(Producto.class);
        return producto.flatMap(p ->
        {
            if (p.getCreatedAt() == null) {
                p.setCreatedAt(new Date());
            }
            return iProductoServices.save(p);
        }).flatMap(p -> created(URI.create("/api/client/".concat(p.getId())))
                .contentType(APPLICATION_JSON)
                .bodyValue(p));
    }

    public Mono<ServerResponse> editar(ServerRequest request) {
        Mono<Producto> producto = request.bodyToMono(Producto.class);
        String id = request.pathVariable("id");
        return producto.flatMap(p -> created(URI.create("/api/client/".concat(p.getId())))
                .contentType(APPLICATION_JSON)
                .body(iProductoServices.update(p, id), Producto.class));
    }

    public Mono<ServerResponse> eliminar(ServerRequest request) {
        String id = request.pathVariable("id");
        return iProductoServices.delete(id).then(ok().build());
    }
}
