package co.jdti.example.webflux.controllers;

import co.jdti.example.webflux.models.documents.Categoria;
import co.jdti.example.webflux.models.documents.Producto;
import co.jdti.example.webflux.services.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.File;
import java.time.Duration;
import java.util.UUID;

@Controller
public class ProductoContoller {

    private final Logger log = LoggerFactory.getLogger(ProductoContoller.class);

    @Autowired
    private IProductoService iProductoService;

    @Value("${config.uploads.path}")
    private String path;

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

    // El parametro Binding Result debe ir despues del parametro que se va a validar con @Valid
    @PostMapping("/form")
    public Mono<String> guardar(@Valid Producto producto, BindingResult result, Model model, @RequestPart FilePart file) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error en el formulario");
            model.addAttribute("botonEnviar", "Guardar");
            log.error(result.getAllErrors().toString());
            return Mono.just("form");
        } else {
            if (!file.filename().isEmpty()) {
                producto.setFoto(UUID.randomUUID().toString() + "_" + file.filename()
                        .replace(" ", "")
                        .replace(":", "")
                        .replace("\\", "")
                        .trim());
            } else {
                log.warn("Sin foto!!");
            }
            return iProductoService.save(producto)
                    .flatMap(pf -> {
                        if (!file.filename().isEmpty()) {
                            return file.transferTo(new File(path + producto.getFoto()));
                        }
                        return Mono.empty();
                    })
                    .thenReturn("redirect:/listar");
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

    @ModelAttribute("categorias")
    public Flux<Categoria> categorias() {
        return iProductoService.findAllCategoria();
    }
}
