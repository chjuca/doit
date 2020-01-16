package com.doitutpl.doit.ui.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.doitutpl.doit.StaticData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;

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
    List<Events> dayliEvents = new ArrayList<>(); // ArrayList que guarda los eventos diarios

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

    // Metodo que establece la notificacion
    private void setNotificacion(Events objEvents){
        setPenndignIntent();
        setOpenPendingIntent();
        setSilencePendingIntent();
        createNotificationChannel();
        createNotification(objEvents);  // Metodo que crea la notificacion
    }

    public void setListaEventos(){
        this.inicializarFirebase();
        obtenerEventos();
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

                dayliEvents.clear();
                for(Events objEvents:listaEvents){

                    // Se transforma la fecha obtenida en formato 'yyyy/mm/dd'
                    String dateEvent = String.format("%s/%s/%s",objEvents.getEvDate().getYear(),
                            objEvents.getEvDate().getMonth(),objEvents.getEvDate().getDay());

                    // Si la fecha transformada es igual al dia actual se agrega los eventos a 'dayliEvents'
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

    // Metodo que establece que accion realizar al dar click en la notificacion
    private void setPenndignIntent(){
        // Llevar a la clase que muestra detalles del evento
        Intent intent = new Intent(this, Navegacion.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Navegacion.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

    }

    // Metodo que establece que accion realizar al dar click en el boton 'Abrir' de la notificacion
    private void setOpenPendingIntent(){
        // Llevar a la clase MostrarEvento
        Intent intent = new Intent(this, Navegacion.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Navegacion.class);
        stackBuilder.addNextIntent(intent);
        openPedingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);
        openPedingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

    }

    // // Metodo que establece que accion realizar al dar click en el boton 'Silenciar de la notificacion
    private void setSilencePendingIntent(){
        // creamos un objeto
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel( NOTIFICACION_ID);
        //time time = new time();
    }

    // Metodo que crea el canal de la notificacion
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NOTIFICACION";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    // Metodo que crea la notificacion
    private void createNotification(Events objEvents){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_event_note_black_24dp); // Se establece el icono de la notificacion traido desde el paquete 'drawle'
        builder.setContentTitle(objEvents.getEvName().toUpperCase()); // Titulo de la notificacion
        builder.setContentText("Hora de inicio: "+objEvents.getEvDate().getHours()+":"+objEvents.getEvDate().getMinutes()); // Hora del evento
        builder.setColor(Color.MAGENTA); // Color de la notificacion
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //builder.setLights(Color.MAGENTA,onMn:1000, offMs:1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000}); // Establece la vibracion de la notificacion
        builder.setDefaults(Notification.DEFAULT_SOUND); // Establece el sonido por defecto que tiene el usuario

        builder.setContentIntent(pendingIntent); // Establece que accion realizar cuando pulsa en la notificacion
        builder.addAction(R.drawable.ic_done_black_24dp,"ABRIR",openPedingIntent); // boton de 'Abrir'
        //builder.addAction(R.drawable.ic_volume_off_black_24dp,"SILENCIAR",silencePedIngIntent); // boton de 'Silenciar'

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
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
        System.out.println(dayliEvents);
        for(Events objEvents:dayliEvents){
            String dateEvent = String.format("%d/%s/%s %s:%s",objEvents.getEvDate().getYear(),
                    objEvents.getEvDate().getMonth(),objEvents.getEvDate().getDay(),
                    objEvents.getEvDate().getHours(),objEvents.getEvDate().getMinutes());
            System.out.println(dateEv +' '+dateEvent);

            // si la fecha del evento es igual a la actual entra
            if(dateEvent.equals(dateEv)){

                if(objEvents.getEvPriority() == 1)
                    // Si la prioridad es 1 --> 'Alta' se recuerda el evento cada 10 segundos, es decir se creara la notificacion 5 veces
                    time.tiempo = 10;
                else
                    if (objEvents.getEvPriority() == 2)
                        // Si la prioridad es 2 --> 'Media' se recuerda el evento cada 20 segundos, es decir se creara la notificacion 3 veces
                        time.tiempo = 20;
                    else
                        // Si la prioridad es 3 --> 'Baja' se recuerda el evento cada 10 segundos, es decir se creara la notificacion 2 veces
                        time.tiempo = 30;
                setNotificacion(objEvents);
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
}
