package co.com.jdti.springmvcsecurity.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	@GetMapping({"", "/", "/index"})
	public String index() {
		return "admin/category/index";
	}
}
