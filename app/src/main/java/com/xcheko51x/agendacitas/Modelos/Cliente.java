package com.xcheko51x.agendacitas.Modelos;

public class Cliente {

    String nomCliente;
    String motivo;

    public Cliente(String nomCliente, String motivo) {
        this.nomCliente = nomCliente;
        this.motivo = motivo;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
