package co.com.jdti.springsecurity;

import co.com.jdti.springsecurity.auth.AuthService;
import co.com.jdti.springsecurity.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AuthService authService) {
		return args -> {
			authService.register(RegisterRequest.builder()
				.firstname("John")
				.lastname("Doe")
				.email("jhon@doe.com")
				.password("12345")
				.build());
			authService.register(RegisterRequest.builder()
				.firstname("John2")
				.lastname("Doe")
				.email("jhon2@doe.com")
				.password("12345")
				.build());
		};
	}
}
