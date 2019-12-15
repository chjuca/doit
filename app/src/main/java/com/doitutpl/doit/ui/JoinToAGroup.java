package com.doitutpl.doit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinToAGroup extends AppCompatActivity {

    //ArrayList<Group> listGroup = new ArrayList<>();
    //Group objGroup = new Group();
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



        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemoss los datos de la UI
                String targetGroupKey = groupKey.getText().toString();
                String targetPassword = groupPass.getText().toString();


                GroupsController groupsController = new GroupsController();
                int resultCode = groupsController.addMember(getApplicationContext(), targetGroupKey, targetPassword, StaticData.currentUser.getEmail());
                handleAddMemberExitCode(resultCode);

            }
        });

    }

    // Metodo que maneja el codigo de salida del proceso addMember de la clase GroupsController
    private void handleAddMemberExitCode(int code) {
        if(code==0){
            // ToDo: Todo correcto, miembro añadido
            // ...

        }else if(code==1){
            // ToDo: Error. El usuario ya pertenece al grupo
            // ...

        }else if(code==2){
            // ToDo: Error. Contraseña incorrecta
            // ...

        }else if(code==3){
            // ToDo: Error. No existe ningún grupo con esa llave
            // ...

        }else if(code==4){
            // ToDo: Error. Proceso cancelado
            // ...

        }else{
            // ToDo: Error inesperado
            // ...

        }
    }



}
