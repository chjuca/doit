package com.doitutpl.doit.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.Models.NotificationsEvents;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.doitutpl.doit.ui.LoadingActivity;
import com.doitutpl.doit.ui.LoginActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NotificationActivity extends AppCompatActivity {


    private PendingIntent pendingIntent;
    private PendingIntent openPedingIntent;
    private PendingIntent readPedIngIntent = null;
    List<NotificationsEvents> listNotifications = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarFirebase();
        time time = new time();
        time.execute();
        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    // Metodo que establece la notificacion
    private void setNotificacion(Events objEvents,int notificacionId){

        //setOpenPendingIntent();
        //setReadPendingIntent(notificacionId);
        if (objEvents.isPublic()){
            notificationGroups(objEvents,notificacionId);
        }else{
            notificationEvents(objEvents,notificacionId);
        }
        //createNotificationEvents(objEvents,notificacionId);  // Metodo que crea la notificacion
    }

    // Metodo que obtiene los eventos
    public void obtenerEventos() {
        final List<Events> listaEvents = new ArrayList<>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        final String [] fechaActual = new String[2];
        fechaActual[0] = formatDate.format(date);
        fechaActual[1] = formatHour.format(date);
        String [] fechaParts = fechaActual[0].split("/");
        String [] horaParts = fechaActual[1].split(":");

        // Se obtiene toda la coleccion de eventos desde firebase
        databaseReference.child(StaticData.EVENTS_NODE_TITLE).orderByChild("evCreatorUser").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEvents.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Events events = objSnapshot.getValue(Events.class); // GET DE EVENTOS
                    listaEvents.add(events);
                }

                listNotifications.clear();
                int channel = 0;
                for(Events objEvents:listaEvents){

                    // Se transforma la fecha obtenida en formato 'yyyy/mm/dd'
                    String dateEvent = String.format("%s/%s/%s",objEvents.getEvDate().getYear(),
                            objEvents.getEvDate().getMonth(),objEvents.getEvDate().getDay());

                    // Si la fecha transformada es igual al dia actual se agrega los eventos a 'dayliEvents'
                    if (dateEvent.equals(fechaActual[0])){
                        listNotifications.add(new NotificationsEvents(objEvents,channel));
                        channel++;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Metodo que establece que accion realizar al dar click en el boton 'Abrir' de la notificacion
    private void setOpenPendingIntent(){
        // Llevar a la clase que muestra detalles del evento
        Intent intent = new Intent(this, Navegacion.class);
        openPedingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    //  Metodo que establece que accion realizar al dar click en el boton 'Silenciar de la notificacion
    private void setReadPendingIntent(int notificacionId){
        // creamos un objeto
        Intent intent = null;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(notificacionId);
    }

    // Metodo que crea el canal de la notificacion
    private void createNotificationChannel(){
        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOTIFICACION";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

         */

    }

    public void notificationNewMember(String groupName,String userName){
        createNotification(new Random().nextInt(1000),
                String.format("%s: %s se unió a tu grupo",groupName.toUpperCase(),userName),"");
        StaticData.groupName = "";
    }

    private void notificationEvents(Events objEvents,int notificacionId){
        createNotification(notificacionId,objEvents.getEvName().toUpperCase(),
                String.format("Hora de inicio: %s:%s",objEvents.getEvDate().getHours(),objEvents.getEvDate().getMinutes()));
    }

    private void notificationGroups(Events objEvents, int notificacionId){
        createNotification(notificacionId,String.format("%s: %s",objEvents.getEvGroups().getNameGroup().toUpperCase(),
                objEvents.getEvName().toUpperCase()),
                String.format("Hora de inicio: %s:%s",objEvents.getEvDate().getHours(),objEvents.getEvDate().getMinutes()));
    }

    // Metodo que crea la notificacion
    private void createNotification(int notificacionId,String title, String description){
        
        String channelId = "notification_channel_1";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, LoginActivity.class);
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
        builder.setContentTitle(title);
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

    // Hilo que permite que se realicen varios procedimientos a la misma ves
    public void hilo(){
        try {
            Thread.sleep(1000); // tiempo dado en milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Metodo principal que valida todas las instancias
    public void ejecutar(){
        obtenerEventos(); // obtenemos todos los eventos
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(); // obtenemos la fecha actual
        String dateEv = formatDate.format(date);  // transformamos a String la fecha
        time time = new time(); // creamos un objeto de tipo tiempo
        time.execute();

        for(NotificationsEvents objNotification:listNotifications){
            String dateEvent = String.format("%d/%s/%s %s:%s",objNotification.getEvent().getEvDate().getYear(),
                    objNotification.getEvent().getEvDate().getMonth(),objNotification.getEvent().getEvDate().getDay(),
                    objNotification.getEvent().getEvDate().getHours(),objNotification.getEvent().getEvDate().getMinutes());
            //System.out.println(dateEv +' '+dateEvent);

            // si la fecha del evento es igual a la actual entra
            if(dateEvent.equals(dateEv)){

                if(objNotification.getEvent().getEvPriority() == 1)
                    // Si la prioridad es 1 --> 'Alta' se recuerda el evento cada 10 segundos, es decir se creara la notificacion 5 veces
                    time.tiempo = 10;
                else
                if (objNotification.getEvent().getEvPriority() == 2)
                    // Si la prioridad es 2 --> 'Media' se recuerda el evento cada 20 segundos, es decir se creara la notificacion 3 veces
                    time.tiempo = 20;
                else
                    // Si la prioridad es 3 --> 'Baja' se recuerda el evento cada 10 segundos, es decir se creara la notificacion 2 veces
                    time.tiempo = 30;

                setNotificacion(objNotification.getEvent(),objNotification.getNotificationId());

            }
        }

    }


    public class time extends AsyncTask<Void,Integer,Boolean> {
        public int tiempo = 1; // tiempo que establece cada que tiempo lanzar la notificacion
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 1;i <= tiempo; i++){
                hilo();
            }
            return true;
        }

        // Metodo que permite que la aplicacion se ejecute en 2 plano
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
        }
    }




    public void goToLoadingActivity(){
        Intent intent = new Intent(NotificationActivity.this, LoadingActivity.class);
        startActivity(intent);
        finish();
    }
}
