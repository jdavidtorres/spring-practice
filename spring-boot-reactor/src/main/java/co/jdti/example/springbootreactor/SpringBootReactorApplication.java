package co.jdti.example.springbootreactor;

import co.jdti.example.springbootreactor.models.Usuario;
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
        Flux<Usuario> usuarios = Flux.just("Hugo Fulano", "Luis Mengano", "Paco Zutano", "Bruce Lee", "Bruce Willis")
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .doOnNext(usuario -> {
                    if (usuario == null || usuario.getNombre().isEmpty()) {
                        throw new RuntimeException("Nombres no pueden ser vacíos");
                    } else {
                        System.out.println(usuario.getNombre() + " " + usuario.getApellido());
                    }
                }).map(usuario -> {
                    usuario.setNombre(usuario.getNombre().toLowerCase());
                    usuario.setApellido(usuario.getApellido().toLowerCase());
                    return usuario;
                });
        usuarios.subscribe(us -> log.info(us.getNombre() + " " + us.getApellido()), error -> log.error(error.getMessage()), new Runnable() {
                    @Override
                    public void run() {
                        log.info("Se ha finalizado la ejecucion del observable con exito!");
                    }
                }
        );
    }
}
