package co.jdti.example.client.app.services;

import co.jdti.example.client.app.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ProductoServicesImpl implements IProductoServices {

    @Autowired
    private WebClient client;

    @Override
    public Flux<Producto> findAll() {
        return client.get()
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Producto.class);
    }

    @Override
    public Mono<Producto> findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return client.get().uri("/{id}", params)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return client.post()
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .acceptCharset(UTF_8)
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Producto> update(Producto producto, String id) {
        return client.put()
                .uri("/{id}", Collections.singletonMap("id", id))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .acceptCharset(UTF_8)
                .bodyValue(producto)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Void> delete(String id) {
        return client.put()
                .uri("/{id}", Collections.singletonMap("id", id))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
