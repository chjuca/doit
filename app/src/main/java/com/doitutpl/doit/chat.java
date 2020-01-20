package com.doitutpl.doit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.doitutpl.doit.Adaptadores.AdapterMensajes;
import com.doitutpl.doit.Adaptadores.HolderMensajes;
import com.doitutpl.doit.Controllers.ChatsController;
import com.doitutpl.doit.Models.Mensaje;
import com.doitutpl.doit.Models.MensajeEnviar;
import com.doitutpl.doit.Models.MensajeRecibir;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class chat extends AppCompatActivity {

    private TextView evNombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private AdapterMensajes adapter;
    private ImageButton btnEnviarFoto;
    private ImageView imgFile;
    private Uri fileUri;


    //============================
    // AQUI SE RECIBE LA KEYSHAT
    //===========================

    final String keyChat = "JBalvin";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int FILE_SEND = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        evNombre = (TextView) findViewById(R.id.evNombre);
        rvMensajes =(RecyclerView) findViewById(R.id.rvMensajes);
        txtMensajes = (EditText) findViewById(R.id.txtMensajes);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = findViewById(R.id.btnEnviarFoto);
        imgFile = findViewById(R.id.fileMensaje);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Chats").child(keyChat);//Sala de chat (nombre)
        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatsController chatsController = new ChatsController();
                chatsController.sendMessage(getApplicationContext(),keyChat, new MensajeEnviar(txtMensajes.getText().toString(), StaticData.currentUser.getDisplayName(),"1", ServerValue.TIMESTAMP),StaticData.currentUser.getEmail());
                txtMensajes.setText("");

            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                        "Imagenes",
                        "Archivos PDF"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(chat.this);
                builder.setTitle("Seleccione lo que desea enviar");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int wich) {
                        if (wich==0){
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                            startActivityForResult(Intent.createChooser(i,"Selecciona una foto"),PHOTO_SEND);
                        }if (wich==1){
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.setType("application/pdf");
                            i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                            startActivityForResult(Intent.createChooser(i,"Selecciona un archivo"),FILE_SEND);
                        }
                    }
                });
                builder.show();
            }
        });

//        imgFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
//                imgFile.getContext().startActivity(intent);
//            }
//        });

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

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILE_SEND && resultCode == RESULT_OK){
            fileUri = data.getData();

            storageReference = storage.getReference("files_chat");//imagenes_chat
            final StorageReference fileReferencia = storageReference.child(((Uri) fileUri).getLastPathSegment());
            fileReferencia.putFile(fileUri);
            fileReferencia.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    MensajeEnviar m = new MensajeEnviar("Se envio un archivo",evNombre.getText().toString(),fileUri.toString(),"3",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });

        }if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();

            storageReference = storage.getReference("imagenes_chat");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(((Uri) u).getLastPathSegment());
            fotoReferencia.putFile(u);
            fotoReferencia.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    MensajeEnviar m = new MensajeEnviar("Se envio una foto",evNombre.getText().toString(), uri.toString(),"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });
        }
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();

            storageReference = storage.getReference("imagenes_chat");//imagenes_chat
            final StorageReference fotoReferencia = storageReference.child(((Uri) u).getLastPathSegment());
            fotoReferencia.putFile(u);
            fotoReferencia.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    MensajeEnviar m = new MensajeEnviar("Se envio una foto",uri.toString(),evNombre.getText().toString(),"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });
        }
    }*/
}


