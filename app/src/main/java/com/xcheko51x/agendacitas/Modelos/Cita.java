package com.xcheko51x.agendacitas.Modelos;

public class Cita {

    private String idCita;
    private  String nomCliente;
    private  String telCliente;
    private  String motivo;
    private  String horaCita;
    private  String diaCita;
    private  String color;

    public Cita(String idCita, String nomCliente, String telCliente, String motivo, String horaCita, String diaCita, String color) {
        this.idCita = idCita;
        this.nomCliente = nomCliente;
        this.telCliente = telCliente;
        this.motivo = motivo;
        this.horaCita = horaCita;
        this.diaCita = diaCita;
        this.color = color;
    }


    public Cita() {

    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getTelCliente() {
        return telCliente;
    }

    public void setTelCliente(String telCliente) {
        this.telCliente = telCliente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getDiaCita() {
        return diaCita;
    }

    public void setDiaCita(String diaCita) {
        this.diaCita = diaCita;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
