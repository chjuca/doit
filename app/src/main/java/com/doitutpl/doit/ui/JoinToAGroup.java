package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinToAGroup extends AppCompatActivity {

    //ArrayList<Group> listGroup = new ArrayList<>();
    //Group objGroup = new Group();
    Button btnJoin;
    EditText groupKey, groupPass;
    TextView textJoin;
    Context context = this;
    Member objMember = new Member();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_grupo);

        btnJoin = findViewById(R.id.btnCreate);
        groupKey = findViewById(R.id.groupName);
        groupPass = findViewById(R.id.groupPass);
        textJoin = findViewById(R.id.textCreate);



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

        textJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), CreateGroup.class);
                startActivityForResult(intent, 0);
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
