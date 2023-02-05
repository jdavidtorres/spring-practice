package co.com.jdti.taller.controller;

import co.com.jdti.taller.model.Car;
import co.com.jdti.taller.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AddCarController {
	
	private final CarService carService;

	@RequestMapping("/add-car")
	public String index(Model model) {
		model.addAttribute("car", new Car());
		return "add-car";
	}

	@PostMapping("/new-car")
	public String addCar(@ModelAttribute Car car) {
		carService.addCar(car);
		return "redirect:/index";
	}
}
