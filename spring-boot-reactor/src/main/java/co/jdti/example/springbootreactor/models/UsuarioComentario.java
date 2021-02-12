package co.jdti.example.springbootreactor.models;

import lombok.ToString;

@ToString
public class UsuarioComentario {

    private Usuario usuario;
    private Comentarios comentarios;

    public UsuarioComentario(Usuario usuario, Comentarios comentarios) {
        this.usuario = usuario;
        this.comentarios = comentarios;
    }
}
