package co.com.jdti.factura.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descripcion;

	private String observacion;

	@Temporal(TemporalType.DATE)
	private Date fechaCreado;

	@ManyToOne(fetch = FetchType.LAZY)
	private Client cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items = new ArrayList<>();

	public Double getTotal() {
		return items.stream().mapToDouble(ItemFactura::calcularImporte).sum();
	}

	@PrePersist
	private void prePersist() {
		this.fechaCreado = new Date();
	}
}
