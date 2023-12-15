package co.com.jdti.walletapp.subscriptions.controllers;

import co.com.jdti.walletapp.subscriptions.model.SubscriptionDTO;
import co.com.jdti.walletapp.subscriptions.services.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

	private final ISubscriptionService iSubscriptionService;

	@GetMapping({"", "/", "/{page}"})
	public String wishIndexView(Model ui, @PathVariable Optional<Integer> page) {
		int pageValue = page.orElse(1);
		Page<SubscriptionDTO> suscriptions = iSubscriptionService.findAll(pageValue);

		ui.addAttribute("title", "Wallet App - Suscripciones");
		ui.addAttribute("currentView", "subscriptions");
		ui.addAttribute("subscriptions", suscriptions.getContent());

		return "subscription/index";
	}
}
