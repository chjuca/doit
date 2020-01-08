package com.doitutpl.doit.Models;

public class GroupEvent {

    // Valores
    public String keyEvent;


    // Constructor vacio necesario para el parseo cuando lleva de firebase
    public GroupEvent() {
    }


    public GroupEvent(String keyEvent) {
        this.keyEvent = keyEvent;
    }

    public String getKeyEvent() {
        return keyEvent;
    }

    public void setKeyEvent(String keyEvent) {
        this.keyEvent = keyEvent;
    }
}
