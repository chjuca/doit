package com.doitutpl.doit.Models;

public class Member {
    private String email;

    public Member(String email) {
        this.email = email;
    }

    public Member() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
