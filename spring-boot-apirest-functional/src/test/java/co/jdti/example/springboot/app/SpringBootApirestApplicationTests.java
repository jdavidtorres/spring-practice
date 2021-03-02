package co.jdti.example.springboot.app;


import co.jdti.example.springboot.app.models.documents.Categoria;
import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SpringBootApirestApplicationTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private IProductoService iProductoService;

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

    @Test
    void verTestOK() {
        Producto producto = iProductoService.findByNombre("Notebook Sony").block();
        assertNotNull(producto);
        client.get()
                .uri("/api/v3/productos/{id}", Collections.singletonMap("id", producto.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("Notebook Sony");
    }

    @Test
    void verTest2OK() {
        Producto producto = iProductoService.findByNombre("Notebook Sony").block();
        assertNotNull(producto);
        client.get()
                .uri("/api/v3/productos/{id}", Collections.singletonMap("id", producto.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .consumeWith(response -> {
                    Producto p = response.getResponseBody();
                    assertNotNull(p);
                    assertEquals(producto.getNombre(), p.getNombre());
                });
    }

    @Test
    void crearTestOK() {
        Categoria categoria = iProductoService.findCategoriaByNombre("Muebles").block();
        Producto producto = new Producto("Mesa comedor", 100.00, categoria);
        client.post()
                .uri("/api/v3/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(producto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .consumeWith(response -> {
                    Producto p = response.getResponseBody();
                    assertNotNull(p);
                    assertEquals(producto.getNombre(), p.getNombre());
                    assertEquals(producto.getCategoria().getNombre(), p.getCategoria().getNombre());
                });
    }

    @Test
    void editarTestOK() {
        Producto producto = iProductoService.findByNombre("Apple iPod").block();
        assertNotNull(producto);
        producto.setNombre("New Apple iPod");
        client.put()
                .uri("/api/v3/productos/{id}", Collections.singletonMap("id", producto.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .consumeWith(response -> {
                    Producto p = response.getResponseBody();
                    assertNotNull(p);
                    assertEquals(producto.getNombre(), p.getNombre());
                });
    }

    @Test
    void eliminarTestOK() {
        Producto producto = iProductoService.findByNombre("Multifuncional Hewlett Packard").block();
        assertNotNull(producto);
        client.delete()
                .uri("/api/v3/productos/{id}", Collections.singletonMap("id", producto.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
        Producto productoDeleted = iProductoService.findByNombre("Multifuncional Hewlett Packard").block();
        assertNull(productoDeleted);
    }
}
