package co.jdti.example.client.app.configurations;

import co.jdti.example.client.app.handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutas(ProductoHandler handler) {

        return route(GET("/api/client"), handler::listar)
                .andRoute(GET("/api/client/{id}"), handler::ver)
                .andRoute(POST("/api/client"), handler::crear)
                .andRoute(PUT("/api/client/{id}"), handler::editar)
                .andRoute(DELETE("/api/client/{id}"), handler::eliminar);
    }
}
