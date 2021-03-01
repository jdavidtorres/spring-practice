package co.jdti.example.springboot.app.config;

import co.jdti.example.springboot.app.handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductoHandler handler) {
        return route(GET("/api/v3/productos").or(GET("/api/v3/productos/all")).and(contentType(MediaType.APPLICATION_JSON)), handler::listar)
                .andRoute(GET("/api/v3/productos/{id}").and(contentType(MediaType.APPLICATION_JSON)), handler::ver)
                .andRoute(POST("/api/v3/productos").and(contentType(MediaType.APPLICATION_JSON)), handler::crear)
                .andRoute(PUT("/api/v3/productos/{id}").and(contentType(MediaType.APPLICATION_JSON)), handler::editar)
                .andRoute(DELETE("/api/v3/productos/{id}").and(contentType(MediaType.APPLICATION_JSON)), handler::eliminar)
                .andRoute(POST("/api/v3/productos/upload/{id}"), handler::upload)
                .andRoute(POST("/api/v3/productos/crear"), handler::crearConFoto);
    }
}
