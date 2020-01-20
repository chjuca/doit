package com.doitutpl.doit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;

public class LoadingActivity extends AppCompatActivity {



    private final int DURACION_SPLASH = 3000;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        loadGroups();

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        // Esperamos durante el tiempo designado mientras cargamos todos los datos
        new Handler().postDelayed(new Runnable(){
            public void run(){
                spinner.setVisibility(View.GONE);
                gotToPrincipalActivity();
            };
        }, DURACION_SPLASH);


    }




    // Métodos para cargar los grupos del usuario
    public void loadGroups(){
        GroupsController groupsController = new GroupsController();
        StaticData.arrayGroups = groupsController.pullUserGroups(getApplicationContext());
    }


    // Hilo que permite que se realicen varios procedimientos a la misma ves
    public void hilo(){
        try {
            Thread.sleep(1000); // tiempo dado en milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    // Método par air a la pantalla principal
    public void gotToPrincipalActivity(){
        Intent intent = new Intent(LoadingActivity.this, Navegacion.class);
        startActivity(intent);
        finish();
    }







}
