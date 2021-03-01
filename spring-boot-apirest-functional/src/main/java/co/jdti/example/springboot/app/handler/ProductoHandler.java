package co.jdti.example.springboot.app.handler;

import co.jdti.example.springboot.app.models.documents.Categoria;
import co.jdti.example.springboot.app.models.documents.Producto;
import co.jdti.example.springboot.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductoHandler {

    @Autowired
    private IProductoService iProductoService;

    @Value("${config.uploads.path}")
    private String path;

    @Autowired
    private Validator validator;

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(iProductoService.findAll(), Producto.class);
    }

    public Mono<ServerResponse> ver(ServerRequest request) {
        String id = request.pathVariable("id");
        return iProductoService.findById(id).flatMap(p -> ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(p))
                .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> crear(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        return productoMono.flatMap(p -> {
            if (p.getCreatedAt() == null) {
                p.setCreatedAt(new Date());
            }
            return iProductoService.save(p);
        }).flatMap(ps -> created(URI.create("/api/v3/productos/".concat(ps.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ps));
    }

    public Mono<ServerResponse> editar(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        String id = request.pathVariable("id");

        Mono<Producto> productoDb = iProductoService.findById(id);

        return productoDb.zipWith(productoMono, (db, req) -> {
            db.setNombre(req.getNombre());
            db.setPrecio(req.getPrecio());
            db.setCategoria(req.getCategoria());
            return db;
        }).flatMap(p -> created(URI.create("/api/v3/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(iProductoService.save(p), Producto.class))
                .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> eliminar(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Producto> productoDb = iProductoService.findById(id);
        return productoDb.flatMap(p -> iProductoService.delete(p)
                .then(ServerResponse.ok().build()))
                .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> upload(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(file -> iProductoService.findById(id)
                        .flatMap(p -> {
                            p.setFoto(UUID.randomUUID().toString() + "_" + file.filename()
                                    .replace(" ", "-")
                                    .replace(":", "")
                                    .replace("\\", ""));
                            return file.transferTo(new File(path + p.getFoto()))
                                    .then(iProductoService.save(p));
                        })).flatMap(p -> created(URI.create("/api/v3/productos/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .switchIfEmpty(noContent().build());
    }

    public Mono<ServerResponse> crearConFoto(ServerRequest request) {
        Mono<Producto> producto = request.multipartData().map(multipart -> {
            FormFieldPart nombre = (FormFieldPart) multipart.toSingleValueMap().get("nombre");
            FormFieldPart precio = (FormFieldPart) multipart.toSingleValueMap().get("precio");
            FormFieldPart categoriaId = (FormFieldPart) multipart.toSingleValueMap().get("categoria.id");
            FormFieldPart categoriaNombre = (FormFieldPart) multipart.toSingleValueMap().get("categoria.nombre");

            Categoria categoria = new Categoria(categoriaNombre.value());
            categoria.setId(categoriaId.value());
            return new Producto(nombre.value(), Double.parseDouble(precio.value()), categoria);
        });
        return request.multipartData().map(multipart -> multipart.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(file -> producto
                        .flatMap(p -> {
                            p.setFoto(UUID.randomUUID().toString() + "_" + file.filename()
                                    .replace(" ", "-")
                                    .replace(":", "")
                                    .replace("\\", ""));
                            p.setCreatedAt(new Date());
                            return file.transferTo(new File(path + p.getFoto()))
                                    .then(iProductoService.save(p));
                        })).flatMap(p -> created(URI.create("/api/v3/productos/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p));
    }

    public Mono<ServerResponse> crearConValidacion(ServerRequest request) {
        Mono<Producto> productoMono = request.bodyToMono(Producto.class);
        return productoMono.flatMap(p -> {
            Errors errors = new BeanPropertyBindingResult(p, Producto.class.getName());
            validator.validate(p, errors);
            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(list -> badRequest()
                                .bodyValue(list));
            } else {
                if (p.getCreatedAt() == null) {
                    p.setCreatedAt(new Date());
                }
                return iProductoService.save(p).flatMap(pdb -> created(URI.create("/api/v3/productos/".concat(pdb.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(pdb));
            }
        });
    }
}
