package com.doitutpl.doit.Controllers;

import android.content.Context;

import com.doitutpl.doit.Adaptadores.AdaptadorCitas;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doitutpl.doit.Models.Events;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;

public class EventsController {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static ArrayList<Events> listEvents = new ArrayList<>();

    public EventsController (){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

    }

    public void chargeEventsFromFirebase(FirebaseUser user, Context context) { // carga los datos desde la base de datos
        FirebaseApp.initializeApp(context);

        databaseReference.child("Events").orderByChild("evCreateUser").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    Events events = objSnapshot.getValue(Events.class);                              // GET DE EVENTOS
                    EventsController.listEvents.add(events);                                         // SE AÑADE A LA LISTA
                    System.out.println(EventsController.listEvents.size()+ "+++SIZE++++");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveEvent(Events Event){

    }

    public ArrayList<Events> getListEvents() { // se obtiene la lista
        System.out.println(EventsController.listEvents);
        return EventsController.listEvents;
    }

    public ArrayList<Events> getEvents(){

        databaseReference.child("Events").orderByChild("evCreateUser").equalTo("renatojobal@gmail.com").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEvents.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()) {

                    Events events = objSnapshot.getValue(Events.class);                              // GET DE EVENTOS
                    listEvents.add(events);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listEvents;
    }

}
