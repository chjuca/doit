package com.doitutpl.doit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.doitutpl.doit.GraficaActivity;
import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;

public class SplashScreen extends AppCompatActivity {

    ImageView ivDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivDev = findViewById(R.id.ivDev);

        Glide.with(this).asGif().load(R.drawable.dispositivos).into(ivDev);

        // LLAMADA A METODO PARA CAMBIAR LA ACTIVITY
        cambiarPantalla();
    }

    public void cambiarPantalla() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, Navegacion.class);
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.TIEMPO_SPLASH_SCREEN));
    }
}
