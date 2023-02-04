package co.com.jdti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "automovil")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Automovil {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String modelo;
	private String marca;
	private String motor;
	private String color;
	private String placa;
	@Column(name = "cantidad_puertas")
	private int cantidadPuertas;
}
