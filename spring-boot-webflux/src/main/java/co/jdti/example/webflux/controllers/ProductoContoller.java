package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return Mono.just("form");
    }

    @PostMapping("/form")
    public Mono<String> guardar(Producto producto) {
        return iProductoService.save(producto).thenReturn("redirect:/listar");
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
