package com.xcheko51x.agendacitas;

import androidx.appcompat.app.AppCompatActivity;
import sun.bob.mcalendarview.MCalendarView;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xcheko51x.agendacitas.Controllers.EventsController;
import com.xcheko51x.agendacitas.Models.Events;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class pruebaCalendario extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase firebaseDatabase;


    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Events> listaEvents = obtenerEventos();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_calendario);

        inicializarFirebase();
    }

    @Override
    protected void onResume() { // metodo que actualiza el renderizado
        super.onResume();
        MCalendarView calendarView = (MCalendarView) findViewById(R.id.calendar);
        renderCalendarView(calendarView);
    }

    private void inicializarFirebase() { // se inicia para actualizar datos desde firebase

        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private ArrayList<Events> obtenerEventos(){ // se pbtiene el evento cargado desde el controlador de evento
        EventsController eventsController = new EventsController();
        eventsController.chargeEventsFromFirebase(user, getApplicationContext());
        return eventsController.getListEvents();
    }

    private void renderCalendarView(MCalendarView calendarView){ /// metodo que se encarga del redenrilado para pintar el calendario
        for (Events objEvents: EventsController.listEvents){ // recorriendo la lista eventos y cargando la fecha
            int year = objEvents.getEvDate().getYear();
            int month = Integer.parseInt(objEvents.getEvDate().getMonth());
            int day = Integer.parseInt(objEvents.getEvDate().getDay());

            System.out.println(year);
            System.out.println(month);
            System.out.println(day);
            calendarView.markDate(year, month , day);

        }

    }
}
