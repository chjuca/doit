package com.doitutpl.doit;

import com.google.firebase.auth.FirebaseUser;



/*
 * En esta clase se dede cargar todos los datos que necesitamos que sean accedidos desde cualqueir parte de la app
 * */

public class StaticData {


    public static FirebaseUser currentUser; // Para guardar el usuario actual


    public static final String eventsNodeTItle = "Events";
    public static final String groupsNodeTitle = "Groups";
    public static final String chatsNodeTItle = "Chats";
    public static final String membersNodeTitle = "members";
}
