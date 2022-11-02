package co.com.jdti.practice.msvccursos.services;


import co.com.jdti.practice.msvccursos.models.Usuario;
import co.com.jdti.practice.msvccursos.models.entity.Curso;
import co.com.jdti.practice.msvccursos.repositories.ICursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CursoServicesImpl implements ICursoService {

	private final ICursoRepository iCursoRepository;

	@Override
	public List<Curso> listar() {
		return (List<Curso>) iCursoRepository.findAll();
	}

	@Override
	public Optional<Curso> porId(Long id) {
		return iCursoRepository.findById(id);
	}

	@Override
	public Curso guardar(Curso curso) {
		return iCursoRepository.save(curso);
	}

	@Override
	public void eliminar(Long id) {
		iCursoRepository.deleteById(id);
	}

	@Override
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		return Optional.empty();
	}
}
