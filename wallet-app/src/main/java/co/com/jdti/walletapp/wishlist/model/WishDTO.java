package co.com.jdti.walletapp.wishlist.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record WishDTO(String id, @NotEmpty String description, @NotNull BigDecimal price, BigDecimal saved) {

	public BigDecimal percentage() {
		if (saved.intValue() == 0) {
			return new BigDecimal("0.0");
		}
		BigDecimal percentage = saved.divide(price, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
		return percentage.setScale(0, RoundingMode.HALF_UP);
	}

	@SneakyThrows
	@Override
	public String toString() {
		return new ObjectMapper().writeValueAsString(this);
	}
}
