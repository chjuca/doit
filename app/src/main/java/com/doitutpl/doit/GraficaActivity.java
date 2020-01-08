package com.doitutpl.doit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.doitutpl.doit.Controllers.EventsController;
import com.doitutpl.doit.Models.Events;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)

public class GraficaActivity extends AppCompatActivity {

    PieChart pieChart;

    List <PieEntry> values = new ArrayList<>();
    ArrayList <Events> events = new ArrayList<>();
    ArrayList <Integer> colors = new ArrayList<>();

    String[] priority = {"ALTO", "MEDIO", "BAJO"};
    int [] count = new int [3];

    EventsController eventsController = new EventsController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        //TRAEMOS LOS EVENTOS DESDE LA BASE DE DATOS

        events = eventsController.getEvents();

        for (Events Objevents : events){
            if (Objevents.getEvPriority() == 1){
                count[0] += 1;
            }else if(Objevents.getEvPriority() == 2){
                count[1] += 1;
            }else{
                count[2] += 1;
            }
        }

        colors.add(getColor(R.color.high));
        colors.add(getColor(R.color.half));
        colors.add(getColor(R.color.low));

        pieChart = findViewById(R.id.pieGrafic);
        pieChart.setDrawEntryLabels(true);
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animateXY(1500,1500);

        for (int i = 0; i <= 2; i++){
           values.add(new PieEntry(i+10, priority[i]));   //Estoy quemando Llamar eventos y funciona :D
        }

        // SETTEAMOS LOS VALORES A LA GRAFICA

        PieDataSet set = new PieDataSet(values, "");
        set.setSliceSpace(5f);
        set.setColors(colors);

        pieChart.setData(new PieData(set));
        pieChart.highlightValue(null);
        pieChart.invalidate();

        pieChart.getDescription().setText("Esta figura muestra la cantidad de eventos agupados por el tipo de prioridad");

    }
}
