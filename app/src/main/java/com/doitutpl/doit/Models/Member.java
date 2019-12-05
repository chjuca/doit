package com.doitutpl.doit.Models;

public class Member {
    private String keyMember;
    private String email;

    public Member(String keyMember, String email) {
        this.keyMember = keyMember;
        this.email = email;
    }

    public Member() {
    }

    public String getKeyMember() {
        return keyMember;
    }

    public void setKeyMember(String keyMember) {
        this.keyMember = keyMember;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
