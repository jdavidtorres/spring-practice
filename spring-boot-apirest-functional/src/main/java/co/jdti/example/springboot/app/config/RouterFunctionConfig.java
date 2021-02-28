package co.jdti.example.springboot.app.config;

import co.jdti.example.springboot.app.handler.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductoHandler handler) {
        return route(GET("/api/v3/productos")
                .or(GET("/api/v3/productos/all")), handler::listar)
                .andRoute(GET("/api/v3/productos/{id}"), handler::ver);
    }
}
