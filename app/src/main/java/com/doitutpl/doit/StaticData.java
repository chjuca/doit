package com.doitutpl.doit;

import com.doitutpl.doit.Models.Group;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;



/*
 * En esta clase se dede cargar todos los datos que necesitamos que sean accedidos desde cualqueir parte de la app
 * */

public class StaticData {


    public static FirebaseUser currentUser; // Para guardar el usuario actual


    public static final String EVENTS_NODE_TITLE = "Events";
    public static final String GROUPS_NODE_TITLE = "Groups";
    public static final String CHATS_NODE_TITLE = "Chats";
    public static final String MEMBERS_NODE_TITLE = "members";
    public static final String USER_PREFERENCES_NAME = "UserPreferences";
    public static final String FIRST_LOGIN = "isFirstLogin";








    public static  String groupName = "";
    public static String currentsKeyChat = "";




    // Array que guarda los grupos
    public static ArrayList<Group> arrayGroups = new ArrayList<>();




}
