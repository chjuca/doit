package com.doitutpl.doit.chats;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doitutpl.doit.Adaptadores.AdapterMensajes;
import com.doitutpl.doit.Controllers.ChatsController;
import com.doitutpl.doit.Models.MensajeEnviar;
import com.doitutpl.doit.Models.MensajeRecibir;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class chat extends AppCompatActivity {

    private TextView evNombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private AdapterMensajes adapter;
    private ImageButton btnEnviarFoto;
    private Uri fileUri;


    //============================
    // AQUI SE RECIBE LA KEYSHAT
    //===========================

    private String keyChat = "JBalvin";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int FILE_SEND = 2;
    private String keyReceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        evNombre = (TextView) findViewById(R.id.evNombre);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensajes = (EditText) findViewById(R.id.txtMensajes);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = findViewById(R.id.btnEnviarFoto);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Chats").child(StaticData.currentsKeyChat);//Sala de chat (nombre)
        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);



            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtMensajes.getText().toString().length()!=0) {
                        ChatsController chatsController = new ChatsController();
                        chatsController.sendMessage(getApplicationContext(), StaticData.currentsKeyChat, new MensajeEnviar(txtMensajes.getText().toString(), StaticData.currentUser.getDisplayName(), "1", ServerValue.TIMESTAMP), StaticData.currentUser.getEmail());
                        txtMensajes.setText("");
                    }
                /*databaseReference.push().setValue(new MensajeEnviar(txtMensajes.getText().toString(),evNombre.getText().toString(),"1", ServerValue.TIMESTAMP));
                txtMensajes.setText("");*/
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
                        if (wich == 0) {
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                            startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
                        }
                        if (wich == 1) {
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.setType("application/pdf");
                            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                            startActivityForResult(Intent.createChooser(i, "Selecciona un archivo"), FILE_SEND);
                        }
                    }
                });
                builder.show();
            }
        });

        /*
        imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                imgFile.getContext().startActivity(intent);
            }
        });
*/

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
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date d = new Date(m.getHora());
                Date date = new Date(); // obtenemos la fecha actual
                String dateM = formatDate.format(date);
                String dateActM = formatDate.format(d);// transformamos a String la fecha
                System.out.println(dateM+" "+dateActM);
                if(dateM.equals(dateActM)) {
                    if (StaticData.currentUser.getDisplayName() != m.getNombre()) {
                        System.out.println(StaticData.dateMessage);
                        if (m.getType_mensaje().equals("3")) {
                            createNotification(0, String.format("%s: Ha enviado un archivo", m.getNombre()));
                        }
                        if (m.getType_mensaje().equals("2")) {
                            createNotification(0, String.format("%s: Ha enviado una foto", m.getNombre()));
                        } else {
                            createNotification(0, String.format("%s: %s: ", m.getNombre(), m.getMensaje()));
                        }


                    }
                }

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

    private void setScrollbar() {
        rvMensajes.scrollToPosition(adapter.getItemCount() - 1);
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

    // Metodo que crea la notificacion
    private void createNotification(int notificacionId, String description){

        String channelId = "notification_channel_1";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, chat.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //Intent intent = new Intent(this, Navegacion.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        ChatsController objChatsController = new ChatsController();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(),channelId
        );
        builder.setSmallIcon(R.drawable.ic_event_note_black_24dp);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText(description);
        //builder.setColor(Color.MAGENTA); // Color de la notificacion
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(String.format("Do It"));
        //builder.setLargeIcon(StaticData.currentUser.);
        builder.setContentText(description);
        builder.setAutoCancel(true);
        builder.setLights(Color.MAGENTA,1000,1000);
        //builder.addAction(R.drawable.ic_open_in_browser_black_24dp,"ABRIR",openPedingIntent); // boton de 'Abrir'
        //builder.addAction(R.drawable.ic_done_all_black_24dp,"Marcar como realizado",readPedIngIntent); // boton de 'Marcar como realizado'
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(notificationManager != null && notificationManager.getNotificationChannel(channelId) == null){
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,"Notification_Channel_1",
                        notificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This notification channel is used to  notify user.");
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if(notificationManager != null){
            notificationManager.notify(notificacionId, notification);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, listGroup.class);
        startActivity(intent);
    }
}


