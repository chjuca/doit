package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.doitutpl.doit.ui.notification.NotificationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

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
        groupKey = findViewById(R.id.keyGroup);
        groupPass = findViewById(R.id.passGroup);
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
                createNotification(new Random().nextInt(1000),
                        String.format("%s: %s se unió a tu grupo",StaticData.groupName.toUpperCase(),StaticData.currentUser.getDisplayName()),"");


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

    // Metodo que crea la notificacion
    private void createNotification(int notificacionId,String title, String description){

        String channelId = "notification_channel_1";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Navegacion.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(),channelId
        );
        builder.setSmallIcon(R.drawable.ic_event_note_black_24dp);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText(description);
        builder.setColor(Color.MAGENTA); // Color de la notificacion
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setContentIntent(pendingIntent);
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



}
