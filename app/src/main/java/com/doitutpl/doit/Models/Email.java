package com.doitutpl.doit.Models;

import android.widget.ImageButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private String userEmail;

    public Email(String userEmail) {
        this.userEmail = userEmail;
    }
    public Email() { }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public boolean validateEmail(String email) {

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

        Matcher mather = pattern.matcher(email);

        return mather.find();
    }
}
