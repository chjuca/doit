package com.doitutpl.doit.Models;

import android.content.Context;
import android.util.Log;

import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.StaticData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Group {


    // Atributos propios
    public String  keyGroup;
    public Member[] members;
    public String chat;
    public String password;
    public String groupAdminEmail;



    public Group(){
        /*Constructor*/
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
        this.password = password;    // Atributos de control
    boolean isValidData = false;

    }

    public String getGroupAdminEmail() {
        return groupAdminEmail;
    }

    public void setGroupAdminEmail(String groupAdminEmail) {
        this.groupAdminEmail = groupAdminEmail;
    }

    // Constructor para usarse desde la UI
    public Group(String keyGroup, String chat, String password, FirebaseUser groupAdmin) {
        this.keyGroup = keyGroup;
        this.chat = chat;
        this.password = password;
        this.groupAdminEmail = groupAdmin.getEmail();
    }

    // Metodo para guardarse en la base de datos
    public void save(Context context){
        GroupsController groupsController = new GroupsController();
        groupsController.save(this, context);
    }

    /* Metodo para construir este objeto a partir ded un JSON de la base de datos
    public Group(){


    }*/




}
