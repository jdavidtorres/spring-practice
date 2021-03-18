package co.jdti.example.microserviciocommons.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICommonServices<E> {

    List<E> findAll();

    Page<E>findAll(Pageable pageable);

    Optional<E> findById(Long id);

    E save(E newStudent);

    void deleteById(Long id);
}
