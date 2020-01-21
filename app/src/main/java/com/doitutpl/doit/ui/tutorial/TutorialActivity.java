package com.doitutpl.doit.ui.tutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.doitutpl.doit.ui.SplashScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Trace;
import android.view.View;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);




        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);




    }




    // Método que controla sis presentar el tutorial o saltar directamente al login
    private void manageTutorial(){

        if(!isFirstLogin()){ // Si es la primera vez que se logea, debería mostrarle el tutorial, si no saltar a la pantalla de login
            // Saltar a la pantalla de login
            goToNextActivity();
        }
        // Continuar con el tutorial
    }

    private boolean isFirstLogin() {
        // Revisar la variable en los shared preferences

        // Ingresamos a las preferencias compartidas
        SharedPreferences prefs =
                getSharedPreferences(StaticData.USER_PREFERENCES_NAME, Context.MODE_PRIVATE);


        // Obtenemos la variable que verifica si el usuario ya ha ingresado anteriormente
        String previousLogin = prefs.getString(StaticData.IS_FIRST_LOGIN, "False");


        if(previousLogin.equals("False")){
            return false;
        }


        return true;
    }



    private void goToNextActivity(){
        Intent intent = new Intent(TutorialActivity.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }


}