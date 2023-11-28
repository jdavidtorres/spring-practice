package co.com.jdti.springmvcsecurity.controllers.users;

import co.com.jdti.springmvcsecurity.helpers.RandomHelper;
import co.com.jdti.springmvcsecurity.models.Account;
import co.com.jdti.springmvcsecurity.models.Role;
import co.com.jdti.springmvcsecurity.services.IAccountService;
import co.com.jdti.springmvcsecurity.services.IRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

	private final IRoleService roleService;

	private final IAccountService accountService;

	private final BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, Model model) {
		model.addAttribute("title", "Login");
		if (error != null) {
			model.addAttribute("message", "Username or password incorrect");
		}
		return "account/login";
	}

	@GetMapping("/register")
	public String register(Model model) {
		Account account = new Account();
		account.setGender("male");

		model.addAttribute("title", "Register");
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("account", account);
		return "account/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute Account account, @RequestParam String[] roles, RedirectAttributes redirectAttributes) {
		try {
			account.setPassword(passwordEncoder.encode(account.getPassword().trim()));
			account.setStatus(true);
			account.setSecurity(RandomHelper.generate(6));
			account.getRoles().clear();

			for (String roleId : roles) {
				Role role = new Role();
				role.setId(roleId);
				account.getRoles().add(role);
			}

			accountService.save(account);
			return "redirect:/account/login";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			log.error(e.getMessage());
			return "redirect:/account/register";
		}
	}

	@GetMapping("/welcome")
	public String welcome(Authentication authentication, Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("username", authentication.getName());
		model.addAttribute("account", authentication.getPrincipal());
		return "account/welcome";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "account/access-denied";
	}
}
