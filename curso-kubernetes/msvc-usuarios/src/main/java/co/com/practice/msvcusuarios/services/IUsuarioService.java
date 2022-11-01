package co.com.practice.msvcusuarios.services;

import co.com.practice.msvcusuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
	List<Usuario> listar();

	Optional<Usuario> porId(Long id);

	Usuario guardar(Usuario usuario);

	void eliminar(Long id);

	Optional<Usuario> porEmail(String email);
}
