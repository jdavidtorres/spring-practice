package co.jdti.example.springbootreactor;

import co.jdti.example.springbootreactor.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> usuarios = new ArrayList<>();
        usuarios.add("Hugo Fulano");
        usuarios.add("Luis Mengano");
        usuarios.add("Paco Zutano");
        usuarios.add("Bruce Lee");
        usuarios.add("Bruce Willis");

        Flux<Usuario> nombres = /*Flux.just("Hugo Fulano", "Luis Mengano", "Paco Zutano", "Bruce Lee", "Bruce Willis")*/
        Flux.fromIterable(usuarios)
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase("bruce"))
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
        nombres.subscribe(us -> log.info(us.getNombre().concat(" ").concat(us.getApellido())), error -> log.error(error.getMessage()), new Runnable() {
                    @Override
                    public void run() {
                        log.info("Se ha finalizado la ejecucion del observable con exito!");
                    }
                }
        );
    }
}
