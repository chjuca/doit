package com.doitutpl.doit.Models;

public class Chats {
    private String keyChat;
    private Messages[] menssages;

    public Chats() {
    }

    public Chats(String keyChat, Messages[] menssages) {
        this.keyChat = keyChat;
        this.menssages = menssages;
    }

    public String getKeyChat() {
        return keyChat;
    }

    public void setKeyChat(String keyChat) {
        this.keyChat = keyChat;
    }

    public Messages[] getMenssages() {
        return menssages;
    }

    public void setMenssages(Messages[] menssages) {
        this.menssages = menssages;
    }
}
