package co.com.jdti.practice.msvccursos.repositories;

import co.com.jdti.practice.msvccursos.entity.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends CrudRepository<Curso, Long> {
}
