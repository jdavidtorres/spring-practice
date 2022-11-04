package co.com.jdti.practice.msvccursos.clients;

import co.com.jdti.practice.msvccursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface IUsuarioClienteRest {

	@GetMapping("/{id}")
	Usuario detalle(@PathVariable Long id);

	@PostMapping
	Usuario crear(@RequestBody Usuario usuario);

	@GetMapping("/usuarios-por-curso")
	List<Usuario> obtenerUsuariosPorCurso(@RequestParam Iterable<Long> ids);
}
