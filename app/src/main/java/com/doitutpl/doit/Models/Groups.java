package com.doitutpl.doit.Models;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Groups {

    private String keyGroup;
    private ArrayList<Member> members = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Groups{" +
                "keyGroup='" + keyGroup + '\'' +
                ", members=" + members +
                ", chat='" + chat + '\'' +
                ", password='" + password + '\'' +
                ", groupAdminEmail='" + groupAdminEmail + '\'' +
                '}';
    }
}
