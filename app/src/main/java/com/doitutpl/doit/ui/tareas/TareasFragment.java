package com.doitutpl.doit.ui.tareas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doitutpl.doit.Adaptadores.AdaptadorClientes;
import com.doitutpl.doit.Models.Cliente;
import com.doitutpl.doit.R;

import java.util.ArrayList;
import java.util.List;

public class TareasFragment extends Fragment {

    TextView tvMensaje;
    RecyclerView rvClientes;

    List<Cliente> listaClientes = new ArrayList<>();
    AdaptadorClientes adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tareas, container, false);

        tvMensaje = root.findViewById(R.id.tvMensaje);
        rvClientes = root.findViewById(R.id.rvClientes);
        rvClientes.setLayoutManager(new GridLayoutManager(getContext(), 1));

        tvMensaje.setText("Selecciona un paciente para ver sus tareas.");

        obtenerClientes();

        return root;
    }

    public void obtenerClientes() {
        listaClientes.clear();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select nomCliente, motivo from citas", null);

        if(fila != null && fila.getCount() != 0) {
            fila.moveToFirst();
            do {

                listaClientes.add(
                        new Cliente(
                                fila.getString(0),
                                fila.getString(1)
                        )
                );
            } while(fila.moveToNext());
        } else {
            Toast.makeText(getContext(), "No hay clientes/pacientes registrados.", Toast.LENGTH_LONG).show();
        }

        db.close();

        //Toast.makeText(MainActivity.this, ""+citas.size(), Toast.LENGTH_SHORT).show();
        adaptador = new AdaptadorClientes(getContext(), listaClientes);
        rvClientes.setAdapter(adaptador);
    }
}