package com.xcheko51x.agendacitas.ui.AccionesDB;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportarDBFragment extends Fragment {

    TextView tvImportAgenda, tvImportTareas;
    Button btnImportarAgenda, btnImportarTareas;

    public ImportarDBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_importar_db, container, false);

        tvImportAgenda = vista.findViewById(R.id.tvImportAgenda);
        tvImportTareas = vista.findViewById(R.id.tvImportTareas);
        btnImportarAgenda = vista.findViewById(R.id.btnImportAgenda);
        btnImportarTareas = vista.findViewById(R.id.btnImportarTareas);

        tvImportAgenda.setText("Si tienes algún archivo de respaldo se cargara.\nSE BORRARAN LOS DATOS ACTUALES.");

        tvImportTareas.setText("Si tines algún archivo de respaldo se cargara.\nSE BORRARAN LOS DATOS ACTUALES.");

        btnImportarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importarAgendaCSV();
            }
        });

        btnImportarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importarTareasCSV();
            }
        });

        return vista;
    }

    public void importarAgendaCSV() {
        limpiarTablas("citas");

        File carpeta = new File(Environment.getExternalStorageDirectory() + "/AgendaCitasApp");

        String archivoAgenda = carpeta.toString() + "/" + "RespaldoAgenda.csv";

        if(!carpeta.exists()) {
            Toast.makeText(getContext(), "NO EXISTE EL ARCHIVO DE RESPALDO.", Toast.LENGTH_LONG).show();
        } else {
            String cadena;
            String[] arreglo;
            try {

                FileReader fileReader = new FileReader(archivoAgenda);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null) {
                    arreglo = cadena.split(",");

                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);
                    SQLiteDatabase db = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();

                    registro.put("nomCliente", arreglo[0]);
                    registro.put("telCliente", arreglo[1]);
                    registro.put("motivo", arreglo[2]);
                    registro.put("hora", arreglo[3]);
                    registro.put("dia", arreglo[4]);
                    registro.put("color", arreglo[5]);

                    // los inserto en la base de datos
                    db.insert("citas", null, registro);

                    db.close();

                    Toast.makeText(getContext(), "SE IMPORTO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { }
        }
    }

    public void importarTareasCSV() {
        limpiarTablas("tareas");

        File carpeta = new File(Environment.getExternalStorageDirectory() + "/AgendaCitasApp");

        String archivoAgenda = carpeta.toString() + "/" + "RespaldoTareas.csv";

        if(!carpeta.exists()) {
            Toast.makeText(getContext(), "NO EXISTE EL ARCHIVO DE RESPALDO.", Toast.LENGTH_LONG).show();
        } else {
            String cadena;
            String[] arreglo;
            try {

                FileReader fileReader = new FileReader(archivoAgenda);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null) {
                    arreglo = cadena.split(",");

                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);
                    SQLiteDatabase db = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();

                    registro.put("nomCliente", arreglo[0]);
                    registro.put("descTarea", arreglo[1]);
                    registro.put("estado", arreglo[2]);

                    // los inserto en la base de datos
                    db.insert("tareas", null, registro);

                    db.close();

                    Toast.makeText(getContext(), "SE IMPORTO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { }
        }
    }

    public void limpiarTablas(String tabla) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        admin.borrarRegistros(tabla, db);

        Toast.makeText(getContext(), "Se limpio los registros de la "+tabla, Toast.LENGTH_SHORT).show();
    }
}
