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

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        manageTutorial();


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




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
        // Le decimos a la app que de aquí en adelante ya no será la primera vez que se logee
        setPreviousLogin("False");

        // Continuar con el tutorial ...
    }

    private boolean isFirstLogin() {
        // Revisar la variable en los shared preferences

//        // Ingresamos a las preferencias compartidas
//        SharedPreferences prefs =
//                getSharedPreferences(StaticData.USER_PREFERENCES_NAME, Context.MODE_PRIVATE);
//
//
//        // Obtenemos la variable que verifica si el usuario ya ha ingresado anteriormente
//        String firstLogin = prefs.getString(StaticData.FIRST_LOGIN, "True");
//        Log.println(Log.INFO, "INFO", "First login: "+firstLogin);
//
//        if(firstLogin.equals("False")){
//            return false;
//        }


        return true;
    }



    private void goToNextActivity(){
        Intent intent = new Intent(TutorialActivity.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }


    private void setPreviousLogin(String value){

        // Ingresamos a las preferencias compartidas
        SharedPreferences prefs =
                getSharedPreferences(StaticData.USER_PREFERENCES_NAME, Context.MODE_PRIVATE);

        // Seteamos la variable
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(StaticData.FIRST_LOGIN, value);
        editor.commit();

    }

}