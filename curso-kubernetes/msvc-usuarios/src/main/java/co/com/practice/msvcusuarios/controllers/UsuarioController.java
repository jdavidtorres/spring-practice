package co.com.practice.msvcusuarios.controllers;

import co.com.practice.msvcusuarios.models.entity.Usuario;
import co.com.practice.msvcusuarios.services.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UsuarioController {

	private final IUsuarioService iUsuarioService;

	private ResponseEntity<Map<String, Object>> validar(BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		result.getFieldErrors().forEach(error -> response.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping("/")
	public List<Usuario> listar() {
		return iUsuarioService.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> detalle(@PathVariable Long id) {
		Optional<Usuario> usuarioOptional = iUsuarioService.porId(id);
		return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}
		if (iUsuarioService.porEmail(usuario.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Correo no válido, intenta con otro."));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}
		Optional<Usuario> usuarioOptional = iUsuarioService.porId(id);
		if (usuarioOptional.isPresent()) {
			Usuario usuarioDb = usuarioOptional.get();
			if (!usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && iUsuarioService.porEmail(usuario.getEmail()).isPresent()) {
				return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Correo no válido, intenta con otro."));
			}
			usuarioDb.setNombre(usuario.getNombre());
			usuarioDb.setEmail(usuario.getEmail());
			usuarioDb.setPassword(usuario.getPassword());
			return ResponseEntity.ok(iUsuarioService.guardar(usuarioDb));
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		if (iUsuarioService.porId(id).isPresent()) {
			iUsuarioService.eliminar(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}
}
