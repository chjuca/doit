package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

                if(targetPassword.length() != 0 && targetNameGroup.length() != 0){
                    Group group = new Group(targetKeyGroup, targetKeyChat, targetNameGroup, targetPassword, StaticData.currentUser);

                    // Guardamos el grupo en la base de datos
                    group.save(context);
                    Toast.makeText(CreateGroup.this,"!Grupo creado Exitosamente¡", Toast.LENGTH_LONG).show();

                    // Mostramos la clave en pantalla
                    groupKey.setText(group.getKeyGroup());
                }else{
                    Toast.makeText(CreateGroup.this,"!Rellene todos los Campos¡", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  groupKey.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText( context, "Copiado en Portapapeles", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
