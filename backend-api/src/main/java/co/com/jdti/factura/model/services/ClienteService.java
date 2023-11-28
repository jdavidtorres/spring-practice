package co.com.jdti.factura.model.services;

import org.springframework.stereotype.Service;

import co.com.jdti.factura.repositories.IClienteDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final IClienteDao clienteDao;
}
