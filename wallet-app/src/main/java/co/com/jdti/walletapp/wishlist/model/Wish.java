package co.com.jdti.walletapp.wishlist.model;

import co.com.jdti.walletapp.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishes")
public class Wish extends BaseEntity {

	private String description;
	private BigDecimal price;
	private BigDecimal saved;
}
