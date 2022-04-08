package co.com.jdti.springbootbackendapirest.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, Long> {

}
