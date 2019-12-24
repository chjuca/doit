package com.doitutpl.doit.Models;

public class Events {

    private String idEvent;
    private String evName;
    private String evDescription;
    private EvDate evDate;
    private int evPriority;
    private String evCreatorUser;
    private  boolean isPublic;
    private Group evGroups;
    private int state; // 0: cancelado    1: pendiente     2: pasado

    public Events() {
    }

    public Events(String idEvent, String evName, String evDescription, EvDate evDate, int evPriority, String evCreatorUser, boolean isPublic, Group evGroups, int state) {
        this.idEvent = idEvent;
        this.evName = evName;
        this.evDescription = evDescription;
        this.evDate = evDate;
        this.evPriority = evPriority;
        this.evCreatorUser = evCreatorUser;
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

    public String getEvCreatorUser() {
        return evCreatorUser;
    }

    public void setEvCreatorUser(String evCreatorUser) {
        this.evCreatorUser = evCreatorUser;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Group getEvGroups() {
        return evGroups;
    }

    public void setEvGroups(Group evGroups) {
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
                ", evCreatorUser='" + evCreatorUser + '\'' +
                ", isPublic=" + isPublic +
                ", evGroups=" + evGroups +
                ", state=" + state +
                '}';
    }
}

