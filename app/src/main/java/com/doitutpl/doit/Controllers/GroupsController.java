package com.doitutpl.doit.Controllers;

import android.content.Context;
import android.util.Log;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.StaticData;
import com.google.firebase.database.DatabaseReference;

public class GroupsController {

    // Metodo para validar los datos antes de subirlo a la base de datos
    public boolean validateData(Group group){
        /*Aqui se debe validar que los datos esten correctos*/
        boolean isValidData = true;
        if(group.keyGroup==null || group.chat==null || group.password==null || group.groupAdminEmail==null){
            isValidData=false;
            Log.println(Log.ERROR,"Invalid Data", "No valid data for save Object Group into firebase");
        }
        return isValidData;
    }


    public void save(Group group, Context context){
        /* Metodo para guardar el grupo en la base de datos */

        // Validamos la informacion
        if(validateData(group)){

            // Obtenemos la conexion
            DatabaseReference databaseReference = Connection.initializeFirebase(context);

            // Lo guardamos en la base de datos
            databaseReference.child(StaticData.groupsNodeTitle).child(group.getKeyGroup()).setValue(group);

        }

    }



}
