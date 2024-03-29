package co.jdti.example.microserviciocommons.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CommonServicesImpl<E, R extends JpaRepository<E, Long>> implements ICommonServices<E> {

    @Autowired
    protected R iRepository;

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return iRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return iRepository.findAll(pageable);
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
