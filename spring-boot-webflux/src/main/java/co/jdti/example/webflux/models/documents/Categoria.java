package co.jdti.example.webflux.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "categorias")
public class Categoria {

    @Id
    private String id;

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
