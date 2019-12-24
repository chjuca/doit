package com.doitutpl.doit.Controllers;

import android.content.Context;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.StaticData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doitutpl.doit.Models.Events;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class EventsController {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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



    // Método para traer todos los eventos propios del usuario
    public ArrayList<Events> pullOwnUserEvents(Context context){

        // ArrayList con los eventos traidos
        final ArrayList<Events> arrayListEvents =  new ArrayList<>();

        // Obtenemos la conexiónt
        final DatabaseReference databaseReference = Connection.initializeFirebase(context).child(StaticData.EVENTS_NODE_TITLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        Events event = child.getValue(Events.class);

                        if(event.getEvCreatorUser().equals(StaticData.currentUser.getEmail())){
                            arrayListEvents.add(event);
                        }


                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return arrayListEvents;
    }



    // Metodo para buscar un Event por su llave como GroupEvent
    public Events searchEventByKey(Context context, String targetKey){
        // Evento a buscar
        final Events[] event = {new Events()};



        // Obtenemos la conexiónt
        final DatabaseReference databaseReference = Connection.initializeFirebase(context).child(StaticData.EVENTS_NODE_TITLE);


        databaseReference.child(targetKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    // Evento encontrado

                    // Serializamos el dataSnapshot a un objeto del tipo Group
                    Events targetEvent = dataSnapshot.getValue(Events.class);



                    event[0] = targetEvent;



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return event[0];

    }

    // Método para traer todos los eventos de un usuario, incluyendo los grupales
    public ArrayList<Events> pullAllEvents(Context context){
        // ArrayList con los eventos traidos
        final ArrayList<Events> arrayListEvents =  new ArrayList<>();


        arrayListEvents.addAll(pullOwnUserEvents(context));
        arrayListEvents.addAll(pullOwnUserEvents(context));


        return  arrayListEvents;

    }



    // Método para traer todos los eventos de todos los grupos a los que pertenece el usuario
    public ArrayList<Events> pullGrupalEvents(Context context){
        // ArrayList con los eventos traidos
        final ArrayList<Events> arrayListEvents =  new ArrayList<>();

        // Primero tenemos que traer todos los grupos a los que pertence el usaurio

        GroupsController groupsController = new GroupsController();
        ArrayList<Group> arrayListGroups = groupsController.pullUserGroups(context);

        // Recorremos ese arraylist y por cada grupo traemos todos sus eventos
        for(int i=0; i < arrayListGroups.size(); i++){
            arrayListEvents.addAll(groupsController.pullGroupEvents(context, arrayListGroups.get(i)));
        }


        return arrayListEvents;


    }






}
