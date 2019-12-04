package com.doitutpl.doit;

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

import java.util.UUID;

public class agregar_grupo extends AppCompatActivity {

    ImageButton btnCopy;
    Button btnGenerateKey;
    EditText groupName, groupPass;
    TextView groupKey;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_grupo);

        btnCopy = findViewById(R.id.btnCopy);
        groupName = findViewById(R.id.groupName);
        groupPass = findViewById(R.id.groupPass);
        groupKey = findViewById(R.id.groupKey);
        btnGenerateKey = findViewById(R.id.btnGenerateKey);

        btnGenerateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupKey.setText(UUID.randomUUID().toString());
                btnGenerateKey.setEnabled(false);
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  groupKey.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context)
            }
        });



    }
}
