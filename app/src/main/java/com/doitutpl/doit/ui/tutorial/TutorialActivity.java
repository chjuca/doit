package com.doitutpl.doit.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;

import com.doitutpl.doit.R;
import com.doitutpl.doit.ui.SplashScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

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

    }

    private boolean isFirstLogin() {
        // Revisar la variable en los shared preferences

    }



    private void goToNextActivity(){
        Intent intent = new Intent(TutorialActivity.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }


}