package co.com.jdti.springbootbackendapirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;
import co.com.jdti.springbootbackendapirest.models.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<Cliente> findAll() {
		return clienteService.findAll();
	}

	@GetMapping("/{id}")
	public Cliente findOne(@PathVariable Long id) {
		return clienteService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		return clienteService.saveCliente(cliente);
	}

	@PutMapping("/{id}")
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente actual = clienteService.findById(id);
		actual.setApellido(cliente.getApellido());
		actual.setNombre(cliente.getNombre());
		actual.setEmail(cliente.getEmail());
		return clienteService.saveCliente(actual);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findOne(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (cliente == null) {
            response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteService.findById(id), HttpStatus.OK);
    }
}
