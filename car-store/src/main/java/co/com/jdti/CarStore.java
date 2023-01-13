package co.com.jdti;

import co.com.jdti.dao.AutomovilRepository;
import co.com.jdti.gui.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.awt.*;

@SpringBootApplication
public class CarStore {

	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(CarStore.class).headless(false).run(args);
		EventQueue.invokeLater(() -> ctx.getBean(Principal.class));
	}

	@Bean
	public CommandLineRunner run(AutomovilRepository automovilRepository) {
		return (args) -> {
		};
	}
}
