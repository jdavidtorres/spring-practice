package co.jdti.example.microserviciocommons.controllers;

import co.jdti.example.microserviciocommons.services.ICommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class CommonController<E, S extends ICommonServices<E>> {

    @Autowired
    protected S iServices;

    @GetMapping
    public ResponseEntity<Iterable<E>> listAll() {
        return ResponseEntity.ok().body(iServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<E> obj = iServices.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody E entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iServices.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        iServices.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
