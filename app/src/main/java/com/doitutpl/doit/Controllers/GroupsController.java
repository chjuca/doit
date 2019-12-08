package com.doitutpl.doit.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.StaticData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class GroupsController {


    // Metodo para validar los datos antes de subirlo a la base de datos
    public boolean validateData(Group group) {
        /*Aqui se debe validar que los datos esten correctos*/
        boolean isValidData = true;
        if (group.keyGroup == null || group.chat == null || group.password == null || group.groupAdminEmail == null) {
            isValidData = false;
            Log.println(Log.ERROR, "Invalid Data", "No valid data for save Object Group into firebase");
        }
        return isValidData;
    }


    public void save(Group group, Context context) {
        /* Metodo para guardar el grupo en la base de datos */

        // Validamos la informacion
        if (validateData(group)) {

            // Obtenemos la conexion
            DatabaseReference databaseReference = Connection.initializeFirebase(context);

            // Lo guardamos en la base de datos
            databaseReference.child(StaticData.groupsNodeTitle).child(group.getKeyGroup()).setValue(group);

        }

    }


    public Group getGroup(Context context, String groupKey) {

        final ArrayList<Group> listGroup = new ArrayList<>();
        final Group[] objGroup = {new Group()};

        // Obtenemos la conexion
        DatabaseReference databaseReference = Connection.initializeFirebase(context);

        databaseReference.child("Groups").orderByChild("keyGroup").equalTo(groupKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Group objGroup = objSnapshot.getValue(Group.class);                 // Obtengo los grupos de firebase que coincidan con la groupKey y lo agrego al arrreglo
                    listGroup.add(objGroup);
                }
                if (listGroup.size() == 0) {
                    Group objGroup = null;                                                     // si el tamaño del arreglo es 0 el grupo no existe, dah!
                } else {
                    objGroup[0] = listGroup.get(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return objGroup[0];
    }


    public void addMember(Context context, final String keyGroup, final String password, String memberEmail) {
        // Obtenemos la conexion
        final DatabaseReference databaseReference = Connection.initializeFirebase(context).child(StaticData.groupsNodeTitle);

        // Verificacion de datos


            databaseReference.child(keyGroup).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) { // Si es que el grupo existe

                        // Serializamos el dataSnapshot a un objeto del tipo Group
                        Group targetGroup = dataSnapshot.getValue(Group.class);


                        System.out.println(targetGroup.password.getClass());
                        System.out.println(password.getClass());

                        // Verificamos que la contraseña coincida con la enviada
                        if (targetGroup.password.equals(password)) { // Si la contraseña coincide
                            Log.println(Log.INFO, "CORRECT", "Correct group password");


                            // Obtenemos el miembros a agregar, en este caso será el mismo que esté logeado
                            MembersController membersController = new MembersController();
                            Member member = membersController.parseMember(StaticData.currentUser);

                            databaseReference.child(keyGroup).child(StaticData.membersNodeTitle).setValue(member)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Write was successful!
                                            Log.println(Log.INFO, "CORRECT", "The member has been added to the group successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Write failed
                                            Log.println(Log.ERROR, "ERROR", "The member could not be added to the group");
                                        }
                                    });

                        }else{
                            Log.println(Log.ERROR, "ERROR", "Incorrect password");
                        }


                    }else{

                        Log.println(Log.ERROR, "ERROR", "The group key did not match with the firebase database");
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


    }




}

