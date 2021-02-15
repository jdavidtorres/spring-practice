package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class ProductoContoller {

    @Autowired
    private IProductRepository iProductRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        Flux<Producto> productos = iProductRepository.findAll();
        model.addAttribute("titulo", "Titulo");
        model.addAttribute("productos", productos);
        return "listar";
    }
}
