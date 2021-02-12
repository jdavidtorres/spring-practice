package co.jdti.example.springbootreactor;

import co.jdti.example.springbootreactor.models.Comentarios;
import co.jdti.example.springbootreactor.models.Usuario;
import co.jdti.example.springbootreactor.models.UsuarioComentario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        ejemploIterable();
        ejemploFlatMap();
        ejemploToString();
        ejemploToCollectList();
        ejemploUsuarioComentarioFlatMap();
        ejemploUsuarioComentarioZipWith();
    }

    public void ejemploIterable() {
        log.info("ejemploIterable()...");
        List<String> usuarios = new ArrayList<>();
        usuarios.add("Hugo Fulano");
        usuarios.add("Luis Mengano");
        usuarios.add("Paco Zutano");
        usuarios.add("Bruce Lee");
        usuarios.add("Bruce Willis");

        Flux<Usuario> nombres = Flux.fromIterable(usuarios)
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
                        log.info("Se ha finalizado la ejecucion del observable con exito!!");
                    }
                }
        );
    }

    private void ejemploFlatMap() {
        log.info("ejemploFlatMap()...");
        List<String> usuarios = new ArrayList<>();
        usuarios.add("Hugo Fulano");
        usuarios.add("Luis Mengano");
        usuarios.add("Paco Zutano");
        usuarios.add("Bruce Lee");
        usuarios.add("Bruce Willis");

        Flux.fromIterable(usuarios)
                .map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .flatMap(usuario -> {
                    if (usuario.getNombre().equalsIgnoreCase("bruce")) {
                        return Mono.just(usuario);
                    } else {
                        return Mono.empty();
                    }
                })
                .map(usuario -> {
                    usuario.setNombre(usuario.getNombre().toLowerCase());
                    usuario.setApellido(usuario.getApellido().toLowerCase());
                    return usuario;
                })
                .subscribe(us -> log.info(us.toString()));
    }

    private void ejemploToString() {
        log.info("ejemploToString()...");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Hugo", "Fulano"));
        usuarios.add(new Usuario("Luis", "Mengano"));
        usuarios.add(new Usuario("Paco", "Zutano"));
        usuarios.add(new Usuario("Bruce", "Lee"));
        usuarios.add(new Usuario("Bruce", "Willis"));

        Flux.fromIterable(usuarios)
                .map(usuario -> usuario.getNombre().toUpperCase().concat(" " + usuario.getApellido()))
                .flatMap(nombre -> {
                    if (nombre.contains("bruce".toUpperCase())) {
                        return Mono.just(nombre);
                    } else {
                        return Mono.empty();
                    }
                })
                .map(String::toLowerCase) // Es igual a usuario -> usuario.toLowerCase
                .subscribe(log::info);
    }

    private void ejemploToCollectList() {
        log.info("ejemploToCollectList()...");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Hugo", "Fulano"));
        usuarios.add(new Usuario("Luis", "Mengano"));
        usuarios.add(new Usuario("Paco", "Zutano"));
        usuarios.add(new Usuario("Bruce", "Lee"));
        usuarios.add(new Usuario("Bruce", "Willis"));

        Flux.fromIterable(usuarios).collectList().subscribe(lista -> {
            log.info(lista.toString());
            lista.forEach(usuario -> log.info(usuario.getNombre()));
        });
    }

    private void ejemploUsuarioComentarioFlatMap() {
        log.info("ejemploUsuarioComentarioFlatMap()...");
        Mono<Usuario> usuarioMono = Mono.fromCallable(this::crearUsuario);
        Mono<Comentarios> comentarioUsuariosMono = Mono.fromCallable(() -> {
            Comentarios comentario = new Comentarios();
            comentario.addComentario("Hola pepe que tal?");
            comentario.addComentario("Estoy haciendo un curso de reactor");
            comentario.addComentario("Saludos");
            return comentario;
        });
        usuarioMono.flatMap(u -> comentarioUsuariosMono.map(c -> new UsuarioComentario(u, c)))
                .subscribe(uc -> log.info(uc.toString()));
    }

    private Usuario crearUsuario() {
        return new Usuario("Jhon", "Doe");
    }

    private void ejemploUsuarioComentarioZipWith() {
        log.info("ejemploUsuarioComentarioZipWith()...");
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Doe"));
        Mono<Comentarios> comentarioUsuariosMono = Mono.fromCallable(() -> {
            Comentarios comentario = new Comentarios();
            comentario.addComentario("Hola pepe que tal?");
            comentario.addComentario("Estoy haciendo un curso de reactor");
            comentario.addComentario("Saludos");
            return comentario;
        });
        Mono<UsuarioComentario> usuarioConComentarios = usuarioMono.zipWith(comentarioUsuariosMono, (usu, com) -> new UsuarioComentario(usu, com));
        usuarioConComentarios.subscribe(uc -> log.info(uc.toString()));
    }
}
