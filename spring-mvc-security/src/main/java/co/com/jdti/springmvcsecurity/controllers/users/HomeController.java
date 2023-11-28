package co.com.jdti.springmvcsecurity.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	@GetMapping({ "", "/", "/index" })
	public String index() {
		return "home/index";
	}
}
