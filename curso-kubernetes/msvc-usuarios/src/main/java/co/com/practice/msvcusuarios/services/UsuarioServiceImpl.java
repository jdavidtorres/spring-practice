package co.com.practice.msvcusuarios.services;

import co.com.practice.msvcusuarios.clientes.CursoClienteRest;
import co.com.practice.msvcusuarios.models.entity.Usuario;
import co.com.practice.msvcusuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final CursoClienteRest cursoClienteRest;

	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> porId(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void eliminar(Long id) {
		usuarioRepository.deleteById(id);
		cursoClienteRest.eliminarCursoUsuarioPorId(id);
	}

	@Override
	public Optional<Usuario> porEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> listarPorIds(Iterable<Long> ids) {
		return (List<Usuario>) usuarioRepository.findAllById(ids);
	}
}
