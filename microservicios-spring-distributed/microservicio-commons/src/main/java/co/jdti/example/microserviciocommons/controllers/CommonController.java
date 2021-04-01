package co.jdti.example.microserviciocommons.controllers;

import co.jdti.example.microserviciocommons.services.ICommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CommonController<E, S extends ICommonServices<E>> {

    @Autowired
    protected S iServices;

    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok().body(iServices.findAll());
    }

    @GetMapping("/pageable")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return ResponseEntity.ok().body(iServices.findAll(pageable));
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
    public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult result) {
        if (result.hasErrors()) {
            return this.validator(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iServices.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        iServices.deleteById(id);
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<?> validator(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
