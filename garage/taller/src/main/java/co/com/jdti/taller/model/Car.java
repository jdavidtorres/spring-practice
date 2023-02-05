package co.com.jdti.taller.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Getter
@Setter
@Builder
@Table(name = "cars")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "car_id", unique = true, nullable = false, updatable = false)
	private String id;

	@Column(nullable = false)
	private String model;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String engine;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private String licensePlate;

	@Column(length = 4, nullable = false)
	private Integer modelYear;

	@Column(name = "doors_quantity", nullable = false)
	private int doorsQuantity;

	@Setter(AccessLevel.NONE)
	@Column(name = "admission_date", nullable = false, updatable = false)
	private Instant admissionDate;

	@PrePersist
	private void saveDate() {
		admissionDate = Instant.now();
	}
}
