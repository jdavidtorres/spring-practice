package co.com.jdti.springbootbackendapirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;
import co.com.jdti.springbootbackendapirest.models.repositories.IClienteDao;

@Service
public class ClienteService {

	@Autowired
	private IClienteDao clienteDao;

	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}

	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	public Cliente saveCliente(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	public void delete(Long id) {
		clienteDao.deleteById(id);
	}
}
