package co.com.jdti.factura.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotEmpty(message = "Nombre no puede estar vacio")
	@Size(min = 4, max = 12)
	@Column(nullable = false, length = 12)
	private String nombre;

	@NotEmpty(message = "Apellido no puede estar vacio")
	@Column(nullable = false, length = 50)
	private String apellido;

	@NotEmpty(message = "E-mail no puede estar vacio")
	@Email(message = "E-mail esta mal formado")
	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaCreado;

	private String foto;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Region region;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Factura> facturas = new ArrayList<>();

	@PrePersist
	private void prePersist() {
		this.fechaCreado = new Date();
	}
}
