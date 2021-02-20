package co.jdti.example.webflux.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "productos")
public class Producto {

    @Id
    private String id;

    @NotEmpty(message = "Es requerido")
    private String nombre;

    @NotNull
    private Double precio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
