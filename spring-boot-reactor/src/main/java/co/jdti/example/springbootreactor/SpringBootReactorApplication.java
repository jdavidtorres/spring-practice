package co.jdti.example.springbootreactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<String> nombres = Flux.just("Hugo", "Luis", "Paco", "Maria")
                // System.out::println tambien puede ser usario para minimizar codigo
                // como se implementa mas adelante
                .doOnNext(elemento -> {
                    if (elemento.isEmpty()) {
                        throw new RuntimeException("Nombres no pueden ser vacíos");
                    } else {
                        System.out.println(elemento);
                    }
                }).map(String::toUpperCase);
        nombres.subscribe(log::info, error -> log.error(error.getMessage()), new Runnable() {
                    @Override
                    public void run() {
                        log.info("Se ha finalizado la ejecucion del observable con exito!");
                    }
                }
        );
    }
}
