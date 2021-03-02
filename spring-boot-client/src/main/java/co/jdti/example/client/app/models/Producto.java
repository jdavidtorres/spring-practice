package co.jdti.example.client.app.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Producto {

    private String id;
    private String nombre;
    private Double precio;
    private Date createdAt;
    private String foto;
    private Categoria categoria;
}
