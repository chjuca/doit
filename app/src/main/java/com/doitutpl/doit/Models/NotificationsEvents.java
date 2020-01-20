package com.doitutpl.doit.Models;

public class NotificationsEvents {

    private Events event;
    private int notificationId;

    public NotificationsEvents(){

    }

    public NotificationsEvents(Events event, int notificationId) {
        this.event = event;
        this.notificationId = notificationId;
    }

    public Events getEvent() { return event; }

    public void setEvent(Events event) { this.event = event; }

    public int getNotificationId() { return notificationId; }

    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
}
