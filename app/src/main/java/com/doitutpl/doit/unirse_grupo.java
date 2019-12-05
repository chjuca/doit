package com.doitutpl.doit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.Models.Groups;
import com.doitutpl.doit.Models.Member;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.UUID;

public class unirse_grupo extends AppCompatActivity {

    ArrayList<Groups> listGroup = new ArrayList<>();
    Groups objGroup = new Groups();
    Button btnJoin;
    EditText groupKey, groupPass;
    Context context = this;
    Member objMember = new Member();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_grupo);

        btnJoin = findViewById(R.id.btnJoin);
        groupKey = findViewById(R.id.groupKey);
        groupPass = findViewById(R.id.groupPass);

        inicializarFirebase();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Groups").orderByChild("keyGroup").equalTo(groupKey.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listGroup.clear();
                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                            Groups objGroup = objSnapshot.getValue(Groups.class);                              // GET DE GRUPOS
                            listGroup.add(objGroup);
                        }
                        objGroup = listGroup.get(0);

                        if (objGroup == null){
                            Toast.makeText(context, "El grupo buscado no existe", Toast.LENGTH_LONG).show();
                        }else{
                            if (objGroup.getPassword().equals(groupPass.getText().toString())){

                                //===================
                                // QUEMANDO DATOS
                                //==================
                                objMember.setEmail("chjuca");// AQUI
                                objMember.setKeyMember("adsaws");
                                objGroup.getMembers().add(objMember);
                                databaseReference.child("Groups").child(objGroup.getKeyGroup()).setValue(objGroup);
                                Toast.makeText(context, "Bienvenido", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                            @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
