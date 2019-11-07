package com.xcheko51x.agendacitas.Modelos;

public class Evento {

    private String idEvent;
    private String evName;
    private String evDescription;
    private String evHour;
    private String evDate;
    private String evColor;
    private String emailUser;

    public Evento() {
    }

    public Evento(String idEvent, String evName, String evDescription, String evHour, String evDate, String evColor, String emailUser) {
        this.idEvent = idEvent;
        this.evName = evName;
        this.evDescription = evDescription;
        this.evHour = evHour;
        this.evDate = evDate;
        this.evColor = evColor;
        this.emailUser = emailUser;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public String getEvDescription() {
        return evDescription;
    }

    public void setEvDescription(String evDescription) {
        this.evDescription = evDescription;
    }

    public String getEvHour() {
        return evHour;
    }

    public void setEvHour(String evHour) {
        this.evHour = evHour;
    }

    public String getEvDate() {
        return evDate;
    }

    public void setEvDate(String evDate) {
        this.evDate = evDate;
    }

    public String getEvColor() {
        return evColor;
    }

    public void setEvColor(String evColor) {
        this.evColor = evColor;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
