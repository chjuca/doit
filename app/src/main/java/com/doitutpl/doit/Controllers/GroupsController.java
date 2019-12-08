package com.doitutpl.doit.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.StaticData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.Map;

public class GroupsController {


    // Metodo para validar los datos antes de subirlo a la base de datos
    public boolean validateData(Group group) {
        /*Aqui se debe validar que los datos esten correctos*/
        boolean isValidData = true;
        if (group.keyGroup == null || group.keyChat == null || group.password == null || group.groupAdminEmail == null) {
            isValidData = false;
            Log.println(Log.ERROR, "Invalid Data", "No valid data for save Object Group into firebase");
        }
        return isValidData;
    }


    // Metodo para guardar un grupo en la base de datos
    static int saveExitCode;
    public int save(Context context, Group group) {
        GroupsController.saveExitCode = -1; // * Exit Code -1, No iniciado
        /* Metodo para guardar el grupo en la base de datos */

        // Validamos la informacion
        if (validateData(group)) {

            // Obtenemos la conexion
            DatabaseReference databaseReference = Connection.initializeFirebase(context);

            // Lo guardamos en la base de datos
            databaseReference.child(StaticData.GROUPS_NODE_TITLE).child(group.getKeyGroup()).setValue(group);

            GroupsController.saveExitCode = 0; // * Exit Code 0; Correcto
        }else {
            GroupsController.saveExitCode = 1; // * Exit Code 1; Data inválida
        }

        return GroupsController.saveExitCode;
    }



    // Metodo usado para añadir un miembro a un grupo
    static int addMemberExitCodeProcess;
    public int addMember(Context context, final String keyGroup, final String password, String memberEmail) {
        GroupsController.addMemberExitCodeProcess = -1; // Exit Code -1 No iniciado
        // Obtenemos la conexión
        final DatabaseReference databaseReference = Connection.initializeFirebase(context).child(StaticData.GROUPS_NODE_TITLE);

        // Verificacion de datos
        databaseReference.child(keyGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { // Si es que el grupo existe

                    // Serializamos el dataSnapshot a un objeto del tipo Group
                    Group targetGroup = dataSnapshot.getValue(Group.class);


                    // Verificamos que la contraseña coincida con la enviada
                    if (targetGroup.password.equals(password)) { // Si la contraseña coincide
                        Log.println(Log.INFO, "CORRECT", "Correct group password");

                        // Obtenemos el miembro a agregar, en este caso será el mismo que esté logeado
                        Member targetMember = MembersController.parseMember(StaticData.currentUser);


                        // Verificamos que el miembro aun no pertenezca al grupo
                        if (isAlreadyMember(targetGroup, targetMember) == false) {
                            DatabaseReference newMemberReference = databaseReference.child(keyGroup).child(StaticData.MEMBERS_NODE_TITLE).push(); // Obtenemos la referencia para agregar el nuevo miembro
                            newMemberReference.setValue(targetMember);                                                                      // Agregamos el nuevo miembro
                            Log.println(Log.INFO, "CORRECT", "The member has been added to the group successfully");
                            GroupsController.addMemberExitCodeProcess = 0;                              // * Exit Code 0 CORRECT
                        } else { // EL usuario ya esta agregado en el grupo

                            Log.println(Log.ERROR, "ERROR", "This user is already on this group");
                            GroupsController.addMemberExitCodeProcess = 1;                              // * Exit Code 1 ERROR
                        }


                    } else { // La contrasena es incorrecta
                        Log.println(Log.ERROR, "ERROR", "Incorrect password");
                        GroupsController.addMemberExitCodeProcess = 2;                                  // * Exit Code 2 ERROR
                    }


                } else { // La group key no existe en firebase

                    Log.println(Log.ERROR, "ERROR", "The group key did not match with the firebase database");
                    GroupsController.addMemberExitCodeProcess = 3;                                      // * Exit Code 3 ERROR
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                GroupsController.addMemberExitCodeProcess = 4;                                          // * Exit Code 4 ERROR
            }
        });

        return GroupsController.addMemberExitCodeProcess;
    }


    // Metodo para saber si un miembro ya está agregado en un grupo
    public boolean isAlreadyMember(Group group, Member member) {
        boolean belongs = false;

        Map<String, Member> membersMap = group.getMembers();

        if (membersMap.containsValue(member)) {
            belongs = true;
        }
        if (membersMap == null) {
            return false;
        }
        if (!membersMap.isEmpty()) {
            for (Map.Entry<String, Member> entry : membersMap.entrySet()) {
                if (entry.getValue().getEmail().equals(member.getEmail())) {
                    return true;
                }
            }
        }
        return belongs;
    }





}

