package co.com.jdti.taller;

import co.com.jdti.taller.model.Car;
import co.com.jdti.taller.service.CarService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CarService carService) {
		return (args) -> {
			carService.addCar(Car.builder()
				.model("Corolla Cross")
				.brand("Toyota")
				.color("Azul")
				.engine("2.0 Hybrid")
				.licensePlate("KON550")
				.doorsQuantity(5)
				.modelYear(2022)
				.build());
			carService.addCar(Car.builder()
				.model("Edge")
				.brand("Ford")
				.color("Gris")
				.engine("2.4")
				.licensePlate("ASG720")
				.doorsQuantity(5)
				.modelYear(2018)
				.build());
		};
	}
}
