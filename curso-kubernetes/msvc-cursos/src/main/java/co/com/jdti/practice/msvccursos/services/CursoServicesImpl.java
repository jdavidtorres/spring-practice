package co.com.jdti.practice.msvccursos.services;


import co.com.jdti.practice.msvccursos.clients.IUsuarioClienteRest;
import co.com.jdti.practice.msvccursos.models.Usuario;
import co.com.jdti.practice.msvccursos.models.entity.Curso;
import co.com.jdti.practice.msvccursos.models.entity.CursoUsuario;
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
	private final IUsuarioClienteRest iUsuarioClienteRest;

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
		Optional<Curso> o = iCursoRepository.findById(cursoId);
		if (o.isPresent()) {
			usuario = iUsuarioClienteRest.detalle(usuario.getId());
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuario.getId());
			curso.addCursoUsuario(cursoUsuario);
			iCursoRepository.save(curso);
			return Optional.of(usuario);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
		Optional<Curso> o = iCursoRepository.findById(cursoId);
		if (o.isPresent()) {
			usuario = iUsuarioClienteRest.crear(usuario);
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuario.getId());
			curso.addCursoUsuario(cursoUsuario);
			iCursoRepository.save(curso);
			return Optional.of(usuario);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		Optional<Curso> o = iCursoRepository.findById(cursoId);
		if (o.isPresent()) {
			usuario = iUsuarioClienteRest.detalle(usuario.getId());
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuario.getId());
			curso.removeCursoUsuario(cursoUsuario);
			iCursoRepository.save(curso);
			return Optional.of(usuario);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Curso> porIdConUsuarios(Long id) {
		Curso curso = iCursoRepository.getReferenceById(id);
		if (!curso.getCursoUsuarios().isEmpty()) {
			List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();
			List<Usuario> usuarios = iUsuarioClienteRest.obtenerUsuariosPorCurso(ids);
			curso.setUsuarios(usuarios);
		}
		return Optional.of(curso);
	}
}
