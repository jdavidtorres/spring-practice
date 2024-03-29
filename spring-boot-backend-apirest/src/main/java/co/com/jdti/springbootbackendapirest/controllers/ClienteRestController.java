package co.com.jdti.springbootbackendapirest.controllers;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;
import co.com.jdti.springbootbackendapirest.models.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteRestController {

    private final ClienteService clienteService;

    @GetMapping
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        Cliente cliente;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        Cliente clienteNew;
        Map<String, Object> response = new HashMap<>();
        try {
            clienteNew = clienteService.saveCliente(cliente);
        } catch (Exception e) {
            response.put("mensaje", "Error al registrar el cliente");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("mensaje", "Cliente registrado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente actual = clienteService.findById(id);
        Cliente clienteUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (actual == null) {
            response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        try {
            actual.setApellido(cliente.getApellido());
            actual.setNombre(cliente.getNombre());
            actual.setEmail(cliente.getEmail());
            clienteUpdated = clienteService.saveCliente(actual);
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el cliente");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("mensaje", "El cliente ha sido actualizado con éxito");
        response.put("cliente", clienteUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar el cliente");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
