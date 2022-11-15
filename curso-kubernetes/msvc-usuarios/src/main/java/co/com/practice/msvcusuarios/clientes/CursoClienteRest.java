package co.com.practice.msvcusuarios.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "msvc-cursos:8002")
public interface CursoClienteRest {

	@DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
	void eliminarCursoUsuarioPorId(@PathVariable Long usuarioId);
}
