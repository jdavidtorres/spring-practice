package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductoContoller {

    @Autowired
    private IProductRepository iProductRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        Flux<Producto> productos = iProductRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
        productos.subscribe();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", productos);
        return "listar";
    }

    @GetMapping("/listar-datadriver")
    public String listarDataDriver(Model model) {
        Flux<Producto> productos = iProductRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).delayElements(Duration.ofSeconds(1));
        productos.subscribe();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
        return "listar";
    }
}
