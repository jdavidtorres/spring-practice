package co.jdti.example.webflux;


import co.jdti.example.webflux.models.documents.Categoria;
import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.services.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    @Autowired
    private ReactiveMongoTemplate template;

    @Autowired
    private IProductoService iProductoService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        template.dropCollection(Producto.class).subscribe();
        template.dropCollection(Categoria.class).subscribe();

        Categoria electronico = new Categoria("Electrónico");
        Categoria deporte = new Categoria("Deporte");
        Categoria computacion = new Categoria("Computación");
        Categoria muebles = new Categoria("Muebles");

        Flux.just(electronico, deporte, computacion, muebles)
                .flatMap(iProductoService::save)
                .thenMany(Flux.just(new Producto("TV Panasocic Pantalla LCD", 456.89, electronico),
                        new Producto("Camara Sony HD Digital", 177.89, electronico),
                        new Producto("Apple iPod", 46.89, electronico),
                        new Producto("Notebook Sony", 846.89, computacion),
                        new Producto("Multifuncional Hewlett Packard", 200.89, computacion),
                        new Producto("Bicicleta Bianchi", 70.89, deporte),
                        new Producto("Notebook HP Omen 17", 2500.89, computacion),
                        new Producto("Mica Cómoda 5 Cajones", 150.89, muebles),
                        new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89, electronico),
                        new Producto("Telefono Samsung S10+", 400.00, electronico))
                        .flatMap(producto -> {
                            producto.setCreatedAt(new Date());
                            return iProductoService.save(producto);
                        }))
                .subscribe();
    }
}
