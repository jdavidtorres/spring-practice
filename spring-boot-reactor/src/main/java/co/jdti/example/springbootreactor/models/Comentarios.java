package co.jdti.example.springbootreactor.models;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Comentarios {

    private List<String> comentarios;

    public Comentarios() {
        this.comentarios = new ArrayList<>();
    }

    public void addComentario(String comentario) {
        this.comentarios.add(comentario);
    }
}
