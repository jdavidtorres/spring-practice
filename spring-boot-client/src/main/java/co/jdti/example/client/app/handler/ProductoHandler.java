package co.jdti.example.client.app.handler;

import co.jdti.example.client.app.models.Producto;
import co.jdti.example.client.app.services.IProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

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
        return errorHandler(ok()
                .contentType(APPLICATION_JSON)
                .body(iProductoServices.findById(id), Producto.class)
                .switchIfEmpty(noContent().build()));
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
                .bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if (errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
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

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response) {
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if (errorResponse.getStatusCode() == NO_CONTENT) {
                Map<String, Object> body = new HashMap<>();
                body.put("timestamp", new Date());
                return status(NO_CONTENT).bodyValue(body);
            }
            return Mono.error(errorResponse);
        });
    }
}
