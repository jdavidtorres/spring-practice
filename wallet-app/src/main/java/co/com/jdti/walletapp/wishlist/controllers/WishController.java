package co.com.jdti.walletapp.wishlist.controllers;

import co.com.jdti.walletapp.wishlist.model.WishDTO;
import co.com.jdti.walletapp.wishlist.services.IWishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Optional;

import static co.com.jdti.walletapp.base.ApplicationConstants.CURRENT_PAGE;
import static co.com.jdti.walletapp.base.ApplicationConstants.CURRENT_VIEW;
import static co.com.jdti.walletapp.base.ApplicationConstants.TITLE;
import static co.com.jdti.walletapp.base.ApplicationConstants.TOTAL_ITEMS;
import static co.com.jdti.walletapp.base.ApplicationConstants.TOTAL_PAGES;
import static co.com.jdti.walletapp.wishlist.WishConstants.REDIRECT_WISH_HOME;
import static co.com.jdti.walletapp.wishlist.WishConstants.WISH_INDEX;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishes")
public class WishController {

	private final IWishService wishService;

	@GetMapping({"", "/", "/{page}"})
	public String wishIndexView(Model ui, @PathVariable Optional<Integer> page) {
		int pageValue = page.orElse(1);
		Page<WishDTO> wishes = wishService.findAll(pageValue);

		if (pageValue > wishes.getTotalPages() && pageValue > 1) {
			return "redirect:/wishes";
		}

		ui.addAttribute(TITLE, "Wallet App - Deseos");
		ui.addAttribute(CURRENT_PAGE, pageValue);
		ui.addAttribute(TOTAL_PAGES, wishes.getTotalPages());
		ui.addAttribute(TOTAL_ITEMS, wishes.getTotalElements());
		ui.addAttribute("wishes", wishes.getContent());
		ui.addAttribute(CURRENT_VIEW, "wishes");

		return WISH_INDEX;
	}

	@GetMapping("/new")
	public String newWish(Model ui) {
		WishDTO newWish = new WishDTO(null, null, new BigDecimal("0.0"), new BigDecimal("0.0"));
		ui.addAttribute(TITLE, "New Wish");
		ui.addAttribute("newWish", newWish);
		ui.addAttribute(CURRENT_VIEW, "wishes");
		return "wishes/new-wish";
	}

	@PostMapping("/save")
	public String saveNewWish(@Valid WishDTO newWish) {
		wishService.create(newWish);
		return REDIRECT_WISH_HOME;
	}

	@GetMapping("/delete/{id}")
	public String deleteWish(@PathVariable String id) {
		wishService.delete(id);
		return REDIRECT_WISH_HOME;
	}

	@GetMapping("/details/{id}")
	public String detailsWishPage(@PathVariable String id, Model ui) {
		Optional<WishDTO> wishOptional = wishService.findById(id);
		ui.addAttribute(TITLE, "Details Wish");
		ui.addAttribute("wish", wishOptional.get());
		ui.addAttribute(CURRENT_VIEW, "wishes");
		return "wishes/detail-wish";
	}

	@GetMapping("/edit/{id}")
	public String editWishPage(@PathVariable String id, Model ui) {
		Optional<WishDTO> wishOptional = wishService.findById(id);
		ui.addAttribute(TITLE, "Edit Wish");
		ui.addAttribute("wish", wishOptional.get());
		ui.addAttribute(CURRENT_VIEW, "wishes");
		return "wishes/edit-wish";
	}

	@PostMapping("/update")
	public String updateWish(@Valid WishDTO wishDTO) {
		wishService.create(wishDTO);
		return REDIRECT_WISH_HOME;
	}
}
