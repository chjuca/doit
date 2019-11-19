package com.xcheko51x.agendacitas.Models;

public class Tarea {

    int idTarea;
    String nomCliente;
    String descTarea;
    String estado;

    public Tarea(int idTarea, String nomCliente, String descTarea, String estado) {
        this.idTarea = idTarea;
        this.nomCliente = nomCliente;
        this.descTarea = descTarea;
        this.estado = estado;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getDescTarea() {
        return descTarea;
    }

    public void setDescTarea(String descTarea) {
        this.descTarea = descTarea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
