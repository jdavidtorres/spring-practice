package co.com.jdti.practice.msvccursos.controllers;

import co.com.jdti.practice.msvccursos.models.Usuario;
import co.com.jdti.practice.msvccursos.models.entity.Curso;
import co.com.jdti.practice.msvccursos.services.ICursoService;
import feign.FeignException;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class CursoController {

	private final ICursoService iCursoService;

	private ResponseEntity<Map<String, Object>> validar(BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		result.getFieldErrors().forEach(error -> response.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping
	public ResponseEntity<List<Curso>> listar() {
		return ResponseEntity.ok(iCursoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Curso> porId(@PathVariable Long id) {
		Optional<Curso> curso = iCursoService.porIdConUsuarios(id);
		return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
	}

	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}
		return ResponseEntity.ok(iCursoService.guardar(curso));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> cursoOptional = iCursoService.porId(id);
		if (cursoOptional.isPresent()) {
			Curso cursoDb = cursoOptional.get();
			cursoDb.setNombre(curso.getNombre());
			return ResponseEntity.status(HttpStatus.CREATED).body(iCursoService.guardar(cursoDb));
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		Optional<Curso> cursoOptional = iCursoService.porId(id);
		if (cursoOptional.isPresent()) {
			iCursoService.eliminar(cursoOptional.get().getId());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/asignar-usuario/{cursoId}")
	public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		Optional<Usuario> optionalUsuario;
		try {
			optionalUsuario = iCursoService.asignarUsuario(usuario, cursoId);
		} catch (FeignException fe) {
			return ResponseEntity.badRequest().build();
		}
		if (optionalUsuario.isPresent()) {
			return ResponseEntity.ok(optionalUsuario.get());
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/crear-usuario/{cursoId}")
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		Optional<Usuario> optionalUsuario;
		try {
			optionalUsuario = iCursoService.crearUsuario(usuario, cursoId);
		} catch (FeignException fe) {
			return ResponseEntity.badRequest().build();
		}
		if (optionalUsuario.isPresent()) {
			return ResponseEntity.ok(optionalUsuario.get());
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/eliminar-usuario/{cursoId}")
	public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		Optional<Usuario> optionalUsuario;
		try {
			optionalUsuario = iCursoService.eliminarUsuario(usuario, cursoId);
		} catch (FeignException fe) {
			return ResponseEntity.badRequest().build();
		}
		if (optionalUsuario.isPresent()) {
			return ResponseEntity.ok(optionalUsuario.get());
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/eliminar-usuario/{usuarioId}")
	public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long usuarioId) {
		iCursoService.eliminarCursoUsuarioPorId(usuarioId);
		return ResponseEntity.ok().build();
	}
}
