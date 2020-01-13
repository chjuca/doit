package com.doitutpl.doit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.doitutpl.doit.Adaptadores.AdapterMensajes;
import com.doitutpl.doit.Adaptadores.MediaResultsAdapter;
import com.doitutpl.doit.Models.Mensaje;
import com.doitutpl.doit.Models.MensajeEnviar;
import com.doitutpl.doit.Models.MensajeRecibir;
import com.doitutpl.doit.utils.PickerUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import java.util.List;

public class chat extends AppCompatActivity implements FilePickerCallback {

    private final static String TAG = chat.class.getSimpleName();

    private TextView evNombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private ImageButton btnEnviarFile;
    private AdapterMensajes adapter;
    private FilePicker filePicker;
    private ListView lvResults;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        evNombre = (TextView) findViewById(R.id.evNombre);
        rvMensajes =(RecyclerView) findViewById(R.id.rvMensajes);
        txtMensajes = (EditText) findViewById(R.id.txtMensajes);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFile = findViewById(R.id.btnEnviarFile);
        lvResults = (ListView) findViewById(R.id.lvResults);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//Sala de chat (nombre)
        filePicker = new FilePicker(this);

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);



        btnEnviarFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFilesSingle();
                Toast.makeText(chat.this, "Se clikeo esta wea", Toast.LENGTH_SHORT).show();
            }
        });





        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar(txtMensajes.getText().toString(),evNombre.getText().toString(),"1", ServerValue.TIMESTAMP));
                txtMensajes.setText("");
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajeRecibir m = dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void pickFilesSingle() {
        filePicker = getFilePicker();
        filePicker.setMimeType("application/pdf");
        filePicker.pickFile();
    }

    private FilePicker getFilePicker() {
        filePicker = new FilePicker(this);
        filePicker.setFilePickerCallback(this);
        filePicker.setCacheLocation(PickerUtils.getSavedCacheLocation(this));
        return filePicker;
    }

    @Override
    public void onFilesChosen(List<ChosenFile> files) {
        for (ChosenFile file : files) {
            Log.d(TAG, "onFilesChosen: " + file);
        }

        MediaResultsAdapter adapter = new MediaResultsAdapter(files, this);
        lvResults.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_FILE && resultCode == RESULT_OK) {
            filePicker.submit(data);
        }
    }

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }




}
