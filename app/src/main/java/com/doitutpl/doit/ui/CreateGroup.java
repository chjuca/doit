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

    Group objGroup = new Group();

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
                objGroup.setKeyGroup(UUID.randomUUID().toString());
                objGroup.setKeyChat(objGroup.getKeyGroup());
                objGroup.setPassword(groupPass.getText().toString());
                objGroup.setNameGroup(groupName.getText().toString());
                objGroup.setGroupAdminEmail(StaticData.currentUser.getEmail());
                groupKey.setText(objGroup.getKeyGroup());

                // Guardamos el grupo en la base de datos
                objGroup.save(context);

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
