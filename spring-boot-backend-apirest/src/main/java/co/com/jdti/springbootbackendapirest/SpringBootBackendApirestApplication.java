package co.com.jdti.springbootbackendapirest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;
import co.com.jdti.springbootbackendapirest.models.repositories.IClienteDao;

@SpringBootApplication
public class SpringBootBackendApirestApplication implements CommandLineRunner {

	@Autowired
	private IClienteDao iClienteDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		iClienteDao.save(
				Cliente.builder().nombre("Jhon").apellido("Doe").email("doe@mail.com").createAt(new Date()).build());
		iClienteDao.save(Cliente.builder().nombre("Linus").apellido("Trovals").email("doe@mail.com")
				.createAt(new Date()).build());
		iClienteDao.save(
				Cliente.builder().nombre("Magma").apellido("Lee").email("doe@mail.com").createAt(new Date()).build());
		iClienteDao.save(Cliente.builder().nombre("Jade").apellido("Leordof").email("doe@mail.com").createAt(new Date())
				.build());
	}
}
