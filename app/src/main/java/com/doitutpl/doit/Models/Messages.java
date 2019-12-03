package com.doitutpl.doit.Models;

public class Messages {
    private String keyMessage;
    private String userEmail;

    public Messages() {
    }

    public Messages(String keyMessage, String userEmail) {
        this.keyMessage = keyMessage;
        this.userEmail = userEmail;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
