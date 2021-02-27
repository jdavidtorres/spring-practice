package co.jdti.example.springboot.app.models.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Document(collection = "categorias")
public class Categoria {

    @Id
    @NotEmpty
    private String id;

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
}
