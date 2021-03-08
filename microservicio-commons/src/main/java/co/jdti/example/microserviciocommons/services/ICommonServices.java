package co.jdti.example.microserviciocommons.services;

import java.util.Optional;

public interface ICommonServices<E> {

    Iterable<E> findAll();

    Optional<E> findById(Long id);

    E save(E newStudent);

    void deleteById(Long id);
}
