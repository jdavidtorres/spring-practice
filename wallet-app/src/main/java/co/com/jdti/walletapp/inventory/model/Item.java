package co.com.jdti.walletapp.inventory.model;

import co.com.jdti.walletapp.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
@Table(name = "items")
public class Item extends BaseEntity {

	@Setter(AccessLevel.PRIVATE)
	private String title;
	private String brand;
	private String model;
	private String operatingSystem;
	private String storageCapacity;
	private String storageType;
	private String color;
	private String ramSize;

	@Column(nullable = false)
	private String processor;
	private String graphicsCard;
	private String imageUrl;
	private String description;

	@Column(nullable = false)
	private Integer quantity;

	private BigDecimal price;
	private String supplierId;
	private BigDecimal purchasePrice;

	public String getTitle() {
		return getBrand() + getModel() + getStorageCapacity() + getStorageType() + getColor() + getRamSize() + getProcessor() + getGraphicsCard() + getOperatingSystem();
	}
}
