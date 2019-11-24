package com.xcheko51x.agendacitas.Models;

import com.xcheko51x.agendacitas.Controllers.MemberController;

public class Groups {

    private String  keyGroup;
    private Member[] members;
    private String chat;
    private String password;

    public Groups() {
    }

    public Groups(String keyGroup, Member[] members, String chat, String password) {
        this.keyGroup = keyGroup;
        this.members = members;
        this.chat = chat;
        this.password = password;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }

    public Member[] getMembers() {
        return members;
    }

    public void setMembers(Member[] members) {
        this.members = members;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
