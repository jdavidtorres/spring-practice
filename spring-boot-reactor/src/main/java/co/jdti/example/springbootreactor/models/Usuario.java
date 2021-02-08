package co.jdti.example.springbootreactor.models;

import lombok.Data;

@Data
public class Usuario {

    private String nombre;
    private String apellido;

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
