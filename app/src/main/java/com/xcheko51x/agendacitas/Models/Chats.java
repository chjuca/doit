package com.xcheko51x.agendacitas.Models;

import com.xcheko51x.agendacitas.Controllers.MessageController;

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
