package co.jdti.example.springboot.app.handler;

import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

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
                .bodyValue(fromValue(p)))
                .switchIfEmpty(noContent().build());
    }
}
