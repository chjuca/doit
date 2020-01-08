package com.doitutpl.doit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doitutpl.doit.Navegacion;
import com.doitutpl.doit.R;
import com.doitutpl.doit.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;



public class SignOutFragment extends Fragment {


    // Declaramos los botones
    Button btnYes, btnCancel;



    public SignOutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_sign_out, container, false);

        // Intanciamos los botones
        btnYes = root.findViewById(R.id.btnYes);
        btnCancel = root.findViewById(R.id.btnCancel);


        // * Agregamos aqui los listener de los botones
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Salir del usuario
                handleSignOut(root.getContext());
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar
                goToNavegacionActivity(root.getContext());
            }
        });



        // Retronamos la vista inflada
        return root;
    }





    public void handleSignOut(Context context){
        signOutFromGoogleAccount();
        updateUI(context);
    }

    public void signOutFromGoogleAccount(){
        FirebaseAuth.getInstance().signOut();
    }

    private void updateUI(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void goToNavegacionActivity(Context context){
        Intent intent = new Intent(context, Navegacion.class);
        startActivity(intent);

    }
}
