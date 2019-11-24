package com.xcheko51x.agendacitas.ui.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xcheko51x.agendacitas.Adaptadores.AdaptadorCitas;
import com.xcheko51x.agendacitas.Models.Events;
import com.xcheko51x.agendacitas.Navegacion;
import com.xcheko51x.agendacitas.R;
import com.xcheko51x.agendacitas.ui.citas.CitasFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private Button btNotificacion;
    private PendingIntent pendingIntent;
    private PendingIntent openPedingIntent;
    private PendingIntent silencePedIngIntent;
    private final static String CHANNEL_ID = "Notificacion";
    private final static int NOTIFICACION_ID = 0;
    List<Events> dayliEvents = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setListaEventos();
        inicializarFirebase();
        time time = new time();
        time.execute();
        startActivity(new Intent(getApplicationContext(), Navegacion.class));
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void setNotificacion(Events objEvents){
        setPenndignIntent();
        setOpenPendingIntent();
        setSilencePendingIntent();
        createNotificationChannel();
        createNotification(objEvents);

    }

    public void setListaEventos(){
        this.inicializarFirebase();
        obtenerEventos();
    }
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
        databaseReference.child("Events").orderByChild("evDate/day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEvents.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Events events = objSnapshot.getValue(Events.class);                              // GET DE EVENTOS
                    listaEvents.add(events);
                }
                //System.out.println(listaEvents.get(0).getEvDate().toString());

                dayliEvents.clear();
                for(Events objEvents:listaEvents){
                    String dateEvent = String.format("%d/%d/%d",objEvents.getEvDate().getYear(),
                            objEvents.getEvDate().getMonth(),objEvents.getEvDate().getDay());
                    if (dateEvent.equals(fechaActual[0])){
                        dayliEvents.add(objEvents);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }



    private void setPenndignIntent(){
        // Llevar a la clase MostrarEvento
        //Intent intent = new Intent(this, MostrarEvento.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(NotificacionActivity.class);
        //stackBuilder.addNextIntent(intent);
        //pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);
        //pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

    }

    private void setOpenPendingIntent(){
        // Llevar a la clase MostrarEvento
        //Intent intent = new Intent(this, MostrarEvento.class);
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(NotificacionActivity.class);
        //stackBuilder.addNextIntent(intent);
        //openPedingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);
        //pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

    }

    private void setSilencePendingIntent(){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel( NOTIFICACION_ID);
        time time = new time();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOTIFICACION";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(Events objEvents){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_event_note_black_24dp);
        builder.setContentTitle(objEvents.getEvName().toUpperCase());
        builder.setContentText("Hora de inicio: "+objEvents.getEvDate().getHours()+":"+objEvents.getEvDate().getMinutes());
        builder.setColor(Color.MAGENTA);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //builder.setLights(Color.MAGENTA,onMn:1000, offMs:1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.ic_done_black_24dp,"ABRIR",openPedingIntent);
        builder.addAction(R.drawable.ic_volume_off_black_24dp,"SILENCIAR",silencePedIngIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutar(){
        obtenerEventos();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String dateEv = formatDate.format(date);
        time time = new time();
        time.execute();
        for(Events objEvents:dayliEvents){
            String dateEvent = String.format("%d/%d/%d %d:%d",objEvents.getEvDate().getYear(),
                    objEvents.getEvDate().getMonth(),objEvents.getEvDate().getDay(),
                    objEvents.getEvDate().getHours(),objEvents.getEvDate().getMinutes());
            System.out.println(dateEvent+' '+dateEv);
            if(dateEvent.equals(dateEv)){
                if(objEvents.getEvPriority() == 1)
                    time.tiempo = 5;
                else
                    if (objEvents.getEvPriority() == 1)
                        time.tiempo = 15;
                    else
                        time.tiempo = 30;
                setNotificacion(objEvents);
            }
        }

    }

    /*
    private ArrayList<Evento> obtenerEventos(final ArrayList<Evento> listaEventos){

        databaseReference.child("Eventos").orderByChild("evDate/day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEventos.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Evento evento = objSnapshot.getValue(Evento.class);                              // GET DE EVENTOS
                    listaEventos.add(evento);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listaEventos;
    }

     */

    public class time extends AsyncTask<Void,Integer,Boolean> {
        public int tiempo = 1;
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 1;i <= tiempo; i++){
                hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            //Toast.makeText(MainActivity.this,"cada 60 segundos",Toast.LENGTH_SHORT).show();
        }
    }
}
