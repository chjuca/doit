package com.doitutpl.doit.Controllers;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {


    // Atributos para la conexión
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;


    //
    public static DatabaseReference initializeFirebase(Context context) {

        FirebaseApp.initializeApp(context);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        return databaseReference;
    }

}
