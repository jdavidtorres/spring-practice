package co.com.jdti.springmvcsecurity.controllers.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/superadmin/invoice")
public class InvoiceController {

	@GetMapping({"", "/", "/index"})
	public String index() {
		return "superadmin/invoice/index";
	}
}
