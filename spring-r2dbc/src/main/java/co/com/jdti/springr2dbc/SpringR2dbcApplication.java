package co.com.jdti.springr2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringR2dbcApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringR2dbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		personRepository.save(Person.builder()
			.age(20)
			.email("mail@mail.com")
			.firstName("John")
			.build()).subscribe();
	}
}
