package co.com.jdti.springbootbackendapirest;

import co.com.jdti.springbootbackendapirest.models.entities.Cliente;
import co.com.jdti.springbootbackendapirest.models.repositories.IClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class SpringBootBackendApiRestApplication implements CommandLineRunner {

	@Autowired
	private IClienteDao iClienteDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApiRestApplication.class, args);
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
