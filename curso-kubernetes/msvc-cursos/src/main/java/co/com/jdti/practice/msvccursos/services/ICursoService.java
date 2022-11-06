package co.com.jdti.practice.msvccursos.services;

import co.com.jdti.practice.msvccursos.models.Usuario;
import co.com.jdti.practice.msvccursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
	List<Curso> listar();

	Optional<Curso> porId(Long id);

	Curso guardar(Curso curso);

	void eliminar(Long id);

	Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

	Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

	Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

	Optional<Curso> porIdConUsuarios(Long id);

	void eliminarCursoUsuarioPorId(Long id);
}
