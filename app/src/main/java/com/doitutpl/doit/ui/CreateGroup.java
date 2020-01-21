package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;

import java.util.UUID;

public class CreateGroup extends AppCompatActivity {

    ImageButton btnCopy;
    Button btnGenerateKey;
    EditText groupName, groupPass;
    TextView groupKey;
    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_grupo);

        btnCopy = findViewById(R.id.btnCopy);
        groupName = findViewById(R.id.groupName);
        groupPass = findViewById(R.id.groupPass);
        groupKey = findViewById(R.id.groupKey);
        btnGenerateKey = findViewById(R.id.btnCreate);


        btnGenerateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Obtenemos los datos desde la interfaz
                String targetKeyGroup = UUID.randomUUID().toString();
                String targetKeyChat = UUID.randomUUID().toString();
                String targetPassword = groupPass.getText().toString();
                String targetNameGroup = groupName.getText().toString();

                // ! Este cosntrutor debe usarse obligatoriamente antes de llamar al método .save()
                // Utilizamos este construcor para que agregue al usuario logeado como primer miembro y como admin
                Group group = new Group(targetKeyGroup, targetKeyChat, targetNameGroup, targetPassword, StaticData.currentUser);


                // Guardamos el grupo en la base de datos
                group.save(context);


                // Mostramos la clave en pantalla
                groupKey.setText(group.getKeyGroup());
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Usa esta llave para unirte al grupo: *" + groupName.getText().toString() + "* en DOIT!+\n" +
                        groupKey.getText().toString() + "\n" +
                        "¡Pide la contraseña al Administrador!";
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Key Chat");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });



    }

}
