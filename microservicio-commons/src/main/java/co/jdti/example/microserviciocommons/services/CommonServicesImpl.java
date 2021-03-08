package co.jdti.example.microserviciocommons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class CommonServicesImpl<E, R extends JpaRepository<E, Long>> implements ICommonServices<E> {

    @Autowired
    private R iRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return iRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(Long id) {
        return iRepository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return iRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        iRepository.deleteById(id);
    }
}
