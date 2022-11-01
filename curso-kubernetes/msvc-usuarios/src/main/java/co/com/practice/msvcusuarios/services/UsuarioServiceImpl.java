package co.com.practice.msvcusuarios.services;

import co.com.practice.msvcusuarios.models.entity.Usuario;
import co.com.practice.msvcusuarios.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

	private final UsuarioRepository usuarioRepository;

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
	}

	@Override
	public Optional<Usuario> porEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
}
