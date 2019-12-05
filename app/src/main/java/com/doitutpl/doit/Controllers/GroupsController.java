package com.doitutpl.doit.Controllers;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.doitutpl.doit.Models.Groups;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupsController {

    Groups objGroup = new Groups();

    public Groups getGroup(FirebaseDatabase firebaseDatabase, DatabaseReference databaseReference, EditText groupKey){

        final ArrayList<Groups> listGroup = new ArrayList<>();

        databaseReference.child("Groups").orderByChild("keyGroup").equalTo(groupKey.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Groups objGroup = objSnapshot.getValue(Groups.class);                 // Obtengo los grupos de firebase que coincidan con la groupKey y lo agrego al arrreglo
                    listGroup.add(objGroup);
                }
                if (listGroup.size() == 0){
                    objGroup= null;                                                     // si el tamaño del arreglo es 0 el grupo no existe, dah!
                }else {
                    objGroup = listGroup.get(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return objGroup;
    }
}

