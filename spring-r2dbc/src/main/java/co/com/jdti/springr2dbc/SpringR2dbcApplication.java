package co.com.jdti.springr2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringR2dbcApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SpringR2dbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
