package com.doitutpl.doit.Models;

import java.util.ArrayList;

public class Groups {

    private String  keyGroup;
    private ArrayList <Member> members = new ArrayList<>();
    private String chat;
    private String password;
    private String groupAdminEmail;

    public Groups() {
    }

    public Groups(String keyGroup, String chat, String password, String groupAdminEmail) {
        this.keyGroup = keyGroup;
        this.chat = chat;
        this.password = password;
        this.groupAdminEmail = groupAdminEmail;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupAdminEmail() {
        return groupAdminEmail;
    }

    public void setGroupAdminEmail(String groupAdminEmail) {
        this.groupAdminEmail = groupAdminEmail;
    }

}
