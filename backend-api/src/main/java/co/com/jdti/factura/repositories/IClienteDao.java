package co.com.jdti.factura.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.jdti.factura.model.entity.Client;

public interface IClienteDao extends JpaRepository<Client, UUID> {

}
