package co.com.jdti.practice.msvccursos.repositories;

import co.com.jdti.practice.msvccursos.models.entity.CursoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoUsuario extends JpaRepository<CursoUsuario, Long> {
	void deleteByUsuarioId(Long id);
}
