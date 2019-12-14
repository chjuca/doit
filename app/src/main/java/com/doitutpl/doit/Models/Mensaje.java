package com.doitutpl.doit.Models;

public class Mensaje {
    private String mensaje;
    private String nombre;
    private String type_mensaje;
    private String hora;

    public Mensaje() {

    }

    public Mensaje(String mensaje, String nombre, String type_mensaje, String hora) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.type_mensaje = type_mensaje;
        this.hora = hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
