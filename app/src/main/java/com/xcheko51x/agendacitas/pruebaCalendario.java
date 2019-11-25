package com.xcheko51x.agendacitas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.vo.DateData;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.xcheko51x.agendacitas.Adaptadores.AdaptadorCitas;
import com.xcheko51x.agendacitas.Models.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class pruebaCalendario extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    DatabaseReference databaseReference;
    final List<Events> listaEvents = new ArrayList<>();
    AdaptadorCitas adaptador;
    RecyclerView rvCitas;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_calendario);

        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);





        Event ev1 = new Event(Color.GREEN, 1575057600000L, "Evento de Prueba");
        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                Log.d("asd", dateClicked.toString());
                try {
                    long epoch = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateClicked.toString()).getTime() / 1000;
                    Log.d("timestamp", dateClicked.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (dateClicked.toString().compareTo("Fri Nov 29 00:00:00 GMT+00:00 2019") == 0){
                    Toast.makeText(context, "Evento de prueba", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay eventos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));

            }
        });

            }

    public void obtenerEventos(){

        databaseReference.child("Events").orderByChild("evCreateUser").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEvents.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()) {

                    Events events = objSnapshot.getValue(Events.class);                              // GET DE EVENTOS
                    listaEvents.add(events);

                    //adaptador = new AdaptadorCitas(getContext(), listaEvents);
                    rvCitas.setAdapter(adaptador);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
