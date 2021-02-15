package co.jdti.example.webflux;


import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.repositories.IProductRepository;
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
    private IProductRepository iProductRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        template.dropCollection(Producto.class).subscribe();
        Flux.just(new Producto("TV Panasocic Pantalla LCD", 750000.00), new Producto("Camara HD Sony", 378900.00))
                .flatMap(producto -> {
                    producto.setCreatedAt(new Date());
                    return iProductRepository.save(producto);
                })
                .subscribe(producto -> log.info(producto.getNombre()));
    }
}
