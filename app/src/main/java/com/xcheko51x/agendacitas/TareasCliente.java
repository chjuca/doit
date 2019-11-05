package com.xcheko51x.agendacitas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xcheko51x.agendacitas.Adaptadores.AdaptadorTareas;
import com.xcheko51x.agendacitas.Modelos.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareasCliente extends AppCompatActivity {

    String nomCliente;

    Button btnAddTarea;
    RecyclerView rvTareas;

    List<Tarea> listaTareas = new ArrayList<>();

    AdaptadorTareas adaptador;

    String[] estados = {"SELECCIONA UN ESTADO", "PENDIENTE", "ENVIADO", "ENTREGADO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_cliente);
        nomCliente = getIntent().getExtras().getString("nomCliente");
        getSupportActionBar().setTitle("TAREAS DE " + nomCliente);

        btnAddTarea = findViewById(R.id.btnAddTarea);
        rvTareas = findViewById(R.id.rvTareas);
        rvTareas.setLayoutManager(new GridLayoutManager(TareasCliente.this, 1));

        obtenerTareasCliente();

        btnAddTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TareasCliente.this);
                LayoutInflater inflater = getLayoutInflater();
                View vista = inflater.inflate(R.layout.dialog_tarea, null);
                builder.setView(vista);

                final TextView tvAccion = vista.findViewById(R.id.tvAccion);
                final EditText etDescTarea = vista.findViewById(R.id.etDescTarea);
                final Spinner spiEstado = vista.findViewById(R.id.spiEstado);
                final ImageButton ibtnLimpiarTexto = vista.findViewById(R.id.ibtnLimpiarTexto);

                tvAccion.setText("AGREGAR TAREA");

                spiEstado.setAdapter(new ArrayAdapter<String>(TareasCliente.this, R.layout.item_spinner, estados));

                ibtnLimpiarTexto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etDescTarea.setText("");
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(etDescTarea.getText().toString().trim().equals("") || spiEstado.getSelectedItemId() == 0) {
                            Toast.makeText(TareasCliente.this, "Se debe de llenar el campo y seleccionar una opción", Toast.LENGTH_SHORT).show();
                        } else {

                            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(TareasCliente.this, "dbSistema", null, 1);
                            SQLiteDatabase db = admin.getWritableDatabase();
                            
                            ContentValues registro = new ContentValues();

                            registro.put("nomCliente", nomCliente);
                            registro.put("descTarea", etDescTarea.getText().toString());
                            registro.put("estado", spiEstado.getSelectedItem().toString());

                            // los inserto en la base de datos
                            db.insert("tareas", null, registro);

                            db.close();
                            
                            obtenerTareasCliente();

                            Toast.makeText(TareasCliente.this, "TAREA REGISTRADA EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setCancelable(true);
                builder.create().show();
            }
        });

    }

    public void obtenerTareasCliente() {
        listaTareas.clear();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(TareasCliente.this, "dbSistema", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from tareas where nomCliente=?", new String[]{nomCliente});

        if(fila != null && fila.getCount() != 0) {
            fila.moveToFirst();
            do {

                listaTareas.add(
                        new Tarea(
                                fila.getInt(0),
                                fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)
                        )
                );

            } while(fila.moveToNext());

            //Toast.makeText(TareasCliente.this, "TOTAL: "+fila.getCount()+"\nSIZE: "+listaTareas.size(), Toast.LENGTH_SHORT).show();
            adaptador = new AdaptadorTareas(TareasCliente.this, listaTareas);
            rvTareas.setAdapter(adaptador);


        } else {
            Toast.makeText(TareasCliente.this, "No hay tareas.", Toast.LENGTH_LONG).show();
        }

        db.close();

    }
}
