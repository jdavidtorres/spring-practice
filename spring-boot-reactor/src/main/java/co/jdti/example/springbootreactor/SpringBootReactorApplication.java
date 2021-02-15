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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        delayIntervalDesdeCreate();
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
        usuarioMono.zipWith(comentarioUsuariosMono, UsuarioComentario::new).subscribe(uc -> log.info(uc.toString()));
    }

    private void ejemploUsuarioComentarioZipWithV2() {
        log.info("ejemploUsuarioComentarioZipWithV2()...");
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Jhon", "Doe"));
        Mono<Comentarios> comentarioUsuariosMono = Mono.fromCallable(() -> {
            Comentarios comentario = new Comentarios();
            comentario.addComentario("Hola pepe que tal?");
            comentario.addComentario("Estoy haciendo un curso de reactor");
            comentario.addComentario("Saludos");
            return comentario;
        });
        Mono<UsuarioComentario> usuarioConComentarios = usuarioMono.zipWith(comentarioUsuariosMono)
                .map(tuple -> {
                    Usuario u = tuple.getT1();
                    Comentarios c = tuple.getT2();
                    return new UsuarioComentario(u, c);
                });
        usuarioConComentarios.subscribe(uc -> log.info(uc.toString()));
    }

    private void ejemploZipWithRangos() {
        log.info("ejemploZipWithRangos()...");
        Flux.just(1, 2, 3, 4, 5)
                .map(i -> (i * 2))
                .zipWith(Flux.range(0, 4), (uno, dos) -> String.format("Primer flux: %d, segundo flux: %d", uno, dos))
                .subscribe(log::info);
    }

    private void ejemploInterval() {
        log.info("ejemploInterval()...");
        Flux<Integer> rango = Flux.range(1, 12);
        Flux<Long> retraso = Flux.interval(Duration.ofSeconds(1L));
        rango.zipWith(retraso, (ra, re) -> ra)
                .doOnNext(i -> log.info(i.toString()))
                .blockLast();
    }

    private void delayElements() {
        log.info("delayElements()...");
        Flux.range(1, 12)
                .delayElements(Duration.ofSeconds(1L))
                .doOnNext(i -> log.info(i.toString()))
                .blockLast();
    }

    private void delayIntervalInfinito() throws InterruptedException {
        log.info("delayIntervalInfinito()...");

        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1L))
                .doOnTerminate(latch::countDown)
                .flatMap(i -> {
                    if (i >= 5) {
                        return Flux.error(new InterruptedException("Solo cuenta hasta 5!!"));
                    }
                    return Flux.just(i);
                })
                .map(i -> "Hola " + i)
                .retry(1)
                .subscribe(log::info, e -> log.error(e.getMessage()));
        latch.await();
    }

    private void delayIntervalDesdeCreate() {
        log.info("delayIntervalDesdeCreate()...");
        Flux.create(emmiter -> {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                private Integer contador = 0;

                @Override
                public void run() {
                    emmiter.next(++contador);
                    if (contador == 10) {
                        timer.cancel();
                        emmiter.complete();
                    }
                }
            }, 1000, 1000);
        })
                .doOnNext(next -> log.info(next.toString()))
                .doOnComplete(() -> log.info("Ha terminado el flujo!"))
                .subscribe();
    }
}
