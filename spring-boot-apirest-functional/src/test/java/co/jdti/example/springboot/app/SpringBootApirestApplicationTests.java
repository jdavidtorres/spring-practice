package co.jdti.example.springboot.app;


import co.jdti.example.springboot.app.models.documents.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootApirestApplicationTests {

    @Autowired
    private WebTestClient client;

    @Test
    void listarTestOK() {
        client.get()
                .uri("/api/v3/productos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Producto.class)
                //.hasSize(10)
                .consumeWith(response -> {
                    List<Producto> productos = response.getResponseBody();
                    assertNotNull(productos);
                    assertTrue(productos.size() > 0);
                });
    }
}
