package com.xcheko51x.agendacitas.ui.AccionesDB;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.R;

import java.io.File;
import java.io.FileWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExportarDBFragment extends Fragment {

    TextView tvExportAgenda, tvExportTareas, tvLimpiarTabla;
    Button btnExportarAgenda, btnExportTareas, btnLimpiarTablas;

    public ExportarDBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_exportar_db, container, false);

        tvExportAgenda = vista.findViewById(R.id.tvExportAgenda);
        btnExportarAgenda = vista.findViewById(R.id.btnExportarAgenda);
        tvExportTareas = vista.findViewById(R.id.tvExportTareas);
        btnExportTareas = vista.findViewById(R.id.btnExportarTareas);
        tvLimpiarTabla = vista.findViewById(R.id.tvLimpiarTabla);
        btnLimpiarTablas = vista.findViewById(R.id.btnLimpiarTablas);

        tvExportAgenda.setText("Esta sección es para generar un archivo con extensión CSV con los datos de su agenda " +
                "para tener un respaldo y para poder ver la información en Excel de ser necesario. ");

        tvExportTareas.setText("Esta sección es para generar un archivo con extensión CSV con los datos de las tareas " +
                "para tener un respaldo y para poder ver la información en Excel de ser necesario. ");

        tvLimpiarTabla.setText("Esta sección es para limpiar todos los registros de las tablas.\nNO SE PUEDE DESHACER LOS CAMBIOS");

        btnExportarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // PERMISOS PARA ANDROID 6 O SUPERIOR
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                }

                exportarAgendaCSV();
            }
        });

        btnExportTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportarTareasCSV();
            }
        });

        btnLimpiarTablas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarTablas("citas");
                limpiarTablas("tareas");
            }
        });

        return vista;
    }

    public void exportarAgendaCSV() {

        File carpeta = new File(Environment.getExternalStorageDirectory() + "/AgendaCitasApp");

        String archivoAgenda = carpeta.toString() + "/" + "RespaldoAgenda.csv";

        boolean isCreate = false;
        if(!carpeta.exists()) {
            isCreate = carpeta.mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter(archivoAgenda);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);

            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery("select * from citas", null);

            if(fila != null && fila.getCount() != 0) {
                fila.moveToFirst();
                do {

                    fileWriter.append(fila.getString(1));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(2));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(3));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(4));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(5));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(6));
                    fileWriter.append("\n");

                } while(fila.moveToNext());
            } else {
                Toast.makeText(getContext(), "No hay citas que respaldar.", Toast.LENGTH_LONG).show();
            }

            db.close();

            fileWriter.close();

            Toast.makeText(getContext(), "SE CREO EL RESPALDO EXITOSAMENTE", Toast.LENGTH_LONG).show();

        } catch (Exception e) { }

    }

    public void exportarTareasCSV() {

        File carpeta = new File(Environment.getExternalStorageDirectory() + "/AgendaCitasApp");

        String archivoAgenda = carpeta.toString() + "/" + "RespaldoTareas.csv";

        boolean isCreate = false;
        if(!carpeta.exists()) {
            isCreate = carpeta.mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter(archivoAgenda);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);

            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery("select * from tareas", null);

            if(fila != null && fila.getCount() != 0) {
                fila.moveToFirst();
                do {

                    fileWriter.append(fila.getString(1));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(2));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(3));
                    fileWriter.append("\n");

                } while(fila.moveToNext());
            } else {
                Toast.makeText(getContext(), "No hay tareas que respaldar.", Toast.LENGTH_LONG).show();
            }

            db.close();

            fileWriter.close();

            Toast.makeText(getContext(), "SE CREO EL RESPALDO EXITOSAMENTE", Toast.LENGTH_LONG).show();

        } catch (Exception e) { }

    }

    public void limpiarTablas(String tabla) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        admin.borrarRegistros(tabla, db);

        Toast.makeText(getContext(), "Se limpio los registros de la "+tabla, Toast.LENGTH_SHORT).show();
    }
}
