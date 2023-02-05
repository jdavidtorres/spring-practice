package co.com.jdti.taller.controller;

import co.com.jdti.taller.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ListCarsController {

	private final CarService carService;

	@RequestMapping("/list-cars")
	public String index(Model model) {
		model.addAttribute("cars", carService.getCarList());
		return "list-cars";
	}
}
