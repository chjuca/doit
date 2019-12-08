package com.doitutpl.doit.Models;

import android.content.Context;

import com.doitutpl.doit.Controllers.GroupsController;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class Group {


    // Atributos propios
    public String  keyGroup;
    public Map<String,Member> members;
    public String keyChat;
    public String password;
    public String groupAdminEmail;



    public Group(){
        /*Constructor*/
    }



    // Constructor para usarse desde la UI
    public Group(String keyGroup, String keyChat, String password, FirebaseUser groupAdmin) {
        this.keyGroup = keyGroup;
        this.keyChat = keyChat;
        this.password = password;
        this.groupAdminEmail = groupAdmin.getEmail();
    }


    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }


    public String getKeyChat() {
        return keyChat;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }

    public void setKeyChat(String keyChat) {
        this.keyChat = keyChat;
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

    // Metodo para guardarse en la base de datos
    public void save(Context context){
        GroupsController groupsController = new GroupsController();
        groupsController.save(context, this);
    }





}
