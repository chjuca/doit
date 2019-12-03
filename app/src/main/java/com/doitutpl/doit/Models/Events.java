package com.doitutpl.doit.Models;

public class Events {

    private String idEvent;
    private String evName;
    private String evDescription;
    private EvDate evDate;
    private int evPriority;
    private String evCreateUser;
    private  boolean isPublic;
    private Groups evGroups;
    private int state; // 0: cancelado    1: pendiente     2: pasado

    public Events() {
    }

    public Events(String idEvent, String evName, String evDescription, EvDate evDate, int evPriority, String evCreateUser, boolean isPublic, Groups evGroups, int state) {
        this.idEvent = idEvent;
        this.evName = evName;
        this.evDescription = evDescription;
        this.evDate = evDate;
        this.evPriority = evPriority;
        this.evCreateUser = evCreateUser;
        this.isPublic = isPublic;
        this.evGroups = evGroups;
        this.state = state;
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

    public EvDate getEvDate() {
        return evDate;
    }

    public void setEvDate(EvDate evDate) {
        this.evDate = evDate;
    }

    public int getEvPriority() {
        return evPriority;
    }

    public void setEvPriority(int evPriority) {
        this.evPriority = evPriority;
    }

    public String getEvCreateUser() {
        return evCreateUser;
    }

    public void setEvCreateUser(String evCreateUser) {
        this.evCreateUser = evCreateUser;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Groups getEvGroups() {
        return evGroups;
    }

    public void setEvGroups(Groups evGroups) {
        this.evGroups = evGroups;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Events{" +
                "idEvent='" + idEvent + '\'' +
                ", evName='" + evName + '\'' +
                ", evDescription='" + evDescription + '\'' +
                ", evDate=" + evDate +
                ", evPriority=" + evPriority +
                ", evCreateUser='" + evCreateUser + '\'' +
                ", isPublic=" + isPublic +
                ", evGroups=" + evGroups +
                ", state=" + state +
                '}';
    }
}

