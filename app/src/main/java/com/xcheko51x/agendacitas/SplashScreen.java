package com.xcheko51x.agendacitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
                Intent intent = new Intent(SplashScreen.this, pruebaCalendario.class);
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.TIEMPO_SPLASH_SCREEN));
    }
}
