package co.com.jdti.practice.msvccursos.repositories;

import co.com.jdti.practice.msvccursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoRepository extends JpaRepository<Curso, Long> {
}
