package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

@Controller
public class ProductoContoller {

    @Autowired
    private IProductoService iProductoService;

    @GetMapping({"/listar", ""})
    public String listar(Model model) {
        Flux<Producto> productos = iProductoService.findAllConNombreUpperCase();
        productos.subscribe();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", productos);
        return "listar";
    }

    @GetMapping("/form")
    public Mono<String> crear(Model model) {
        model.addAttribute("titulo", "Formulario de producto");
        model.addAttribute("producto", new Producto());
        model.addAttribute("botonEnviar", "Guardar");
        return Mono.just("form");
    }

    @PostMapping("/form")
    public Mono<String> guardar(@Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error en el formulario");
            model.addAttribute("botonEnviar", "Guardar");
            return Mono.just("form");
        } else {
            return iProductoService.save(producto).thenReturn("redirect:/listar");
        }
    }

    @GetMapping("/form/{id}")
    public Mono<String> editar(@PathVariable String id, Model model) {
        Mono<Producto> productoMono = iProductoService.findById(id);
        model.addAttribute("tittulo", "Editar producto");
        model.addAttribute("producto", productoMono);
        model.addAttribute("botonEnviar", "Editar");
        return Mono.just("form");
    }

    @GetMapping("/form-v2/{id}")
    public Mono<String> editarV2(@PathVariable String id, Model model) {
        return iProductoService.findById(id).doOnNext(p -> {
            model.addAttribute("tittulo", "Editar producto");
            model.addAttribute("producto", p);
            model.addAttribute("botonEnviar", "Editar");
        })
                .defaultIfEmpty(new Producto())
                .flatMap(p -> {
                    if (p.getId() == null) {
                        return Mono.error(new InterruptedException("No existe el producto."));
                    }
                    return Mono.just(p);
                })
                .thenReturn("form")
                .onErrorResume(ex -> Mono.just("redirect:/listar?error=No+existe+el+producto"));
    }

    @GetMapping("/eliminar/{id}")
    public Mono<String> eliminar(@PathVariable String id) {
        return iProductoService.findById(id)
                .defaultIfEmpty(new Producto())
                .flatMap(p -> {
                    if (p.getId() == null) {
                        return Mono.error(new InterruptedException("No existe el producto"));
                    }
                    return Mono.just(p);
                }).flatMap(p -> iProductoService.delete(p))
                .thenReturn("redirect:/listar")
                .onErrorResume(ex -> Mono.just("redirect:/listar?error=No+existe+el+producto"));
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {
        Flux<Producto> productos = iProductoService.findAllConNombreUpperCase().delayElements(Duration.ofSeconds(1));
        productos.subscribe();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
        return "listar";
    }

    @GetMapping("/listar-full")
    public String listarFull(Model model) {
        Flux<Producto> productos = iProductoService.findAllConNombreUpperCaseRepeat();
        productos.subscribe();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", productos);
        return "listar";
    }
}
