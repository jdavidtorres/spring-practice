package co.com.practice.msvcusuarios.controllers;

import co.com.practice.msvcusuarios.models.entity.Usuario;
import co.com.practice.msvcusuarios.services.IUsuarioService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UsuarioController {

	private final IUsuarioService iUsuarioService;

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
	public Usuario guardar(@RequestBody Usuario usuario) {
		return iUsuarioService.guardar(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioOptional = iUsuarioService.porId(id);
		if (usuarioOptional.isPresent()) {
			Usuario usuarioDb = usuarioOptional.get();
			usuarioDb.setNombre(usuario.getNombre());
			usuarioDb.setEmail(usuario.getPassword());
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
