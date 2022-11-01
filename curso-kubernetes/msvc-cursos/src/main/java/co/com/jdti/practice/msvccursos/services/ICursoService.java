package co.com.jdti.practice.msvccursos.services;

import co.com.jdti.practice.msvccursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
	List<Curso> listar();

	Optional<Curso> porId(Long id);

	Curso guardar(Curso curso);

	void eliminar(Long id);
}
