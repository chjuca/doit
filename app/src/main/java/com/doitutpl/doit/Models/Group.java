package com.doitutpl.doit.Models;

import android.content.Context;
import android.util.Log;

import com.doitutpl.doit.StaticData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Group {


    // Atributos propios
    public String  keyGroup;
    public Member[] members;
    public String chat;
    public String password;
    public User groupAdmin;

    // Atributos para la conexión
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // Atributos de control
    boolean isValidData = false;



    public Group(){
        /*Constructor*/
    }



    // Constructor para usarse desde la UI
    public Group(String keyGroup, String chat, String password, User groupAdmin) {
        this.keyGroup = keyGroup;
        this.chat = chat;
        this.password = password;
        this.groupAdmin = groupAdmin;
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

    public User getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(User groupAdmin) {
        this.groupAdmin = groupAdmin;
    }



    public void save(Context context, User groupAdmin){
        /* Metodo para guardar el grupo en la base de datos */

        // Agregamos el usuario como admin del grupo
        this.groupAdmin = groupAdmin;

        // Verificamos que la data del grupo sea correcta
        if(this.validateData()){
            // Inicilizamos la conexión con la base de datos
            initializeFirebase(context);

            // Lo guardamos en la base de datos
            databaseReference.child(StaticData.groupsNodeTitle).child(this.getKeyGroup()).setValue(this);
        }

    }

    //
    private void initializeFirebase(Context context) {

        FirebaseApp.initializeApp(context);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    // Metodo para validar los datos antes de subirlo a la base de datos
    public boolean validateData(){
        /*Aqui se debe validar que los datos esten correctos*/
        this.isValidData = true;
        if(keyGroup==null || chat==null || password==null || groupAdmin==null){
            isValidData=false;
            Log.println(Log.ERROR,"Invalid Data", "No valid data for save Object Group into firebase");
        }
        return isValidData;
    }

    /* Metodo para construir este objeto a partir ded un JSON de la base de datos
    public Group(){


    }*/




}
