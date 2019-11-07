package com.xcheko51x.agendacitas.ui.citas;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xcheko51x.agendacitas.Adaptadores.AdaptadorCitas;
import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.Modelos.Cita;
import com.xcheko51x.agendacitas.Modelos.Evento;
import com.xcheko51x.agendacitas.MostrarTodos;
import com.xcheko51x.agendacitas.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

;

public class CitasFragment extends Fragment {

    ImageButton ibtnAdd, ibtnShowAll;
    RecyclerView rvCitas;
    EditText evName, evDescription, evDate;
    TextView evHour;
    ImageButton ibtnHora;
    Spinner spiDias, spiDiasMain, spiColores;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    AdaptadorCitas adaptador;
    List<Cita> listaCitas = new ArrayList<>();
    List<Evento> listaEventos = new ArrayList<>();

    String[] dias = {"SELECCIONA UN DIA", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    String[] colores = {"GRIS", "VERDE", "NARANJA", "NEGRO", "PURPURA"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_citas, container, false);


        ibtnAdd = root.findViewById(R.id.ibtnAgregar);
        ibtnShowAll = root.findViewById(R.id.ibtnMostrarTodas);
        spiDiasMain = root.findViewById(R.id.spiDiasMain);
        rvCitas = root.findViewById(R.id.rvCitas);
        rvCitas.setLayoutManager(new GridLayoutManager(getContext(), 1));

        obtenerDiaActual();
        inicializarFirebase();
        obtenerEventos();

        spiDiasMain.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, dias));

        spiDiasMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, ""+parent.getSelectedItem(), Toast.LENGTH_SHORT).show();
/*                if(parent.getSelectedItem().equals("Lunes")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Martes")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Miercoles")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Jueves")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Viernes")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Sabado")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                } else if(parent.getSelectedItem().equals("Domingo")) {
                    obtenerCitas(""+parent.getSelectedItem(), listaCitas);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();

                View vista = inflater.inflate(R.layout.dialog_agregar_cita, null);
                builder.setView(vista);

                evName = vista.findViewById(R.id.evName);
                evDescription = vista.findViewById(R.id.evDescription);
                evHour = vista.findViewById(R.id.evHour);
                ibtnHora = vista.findViewById(R.id.ibtnHora);
                spiDias = vista.findViewById(R.id.spiDias);
                evDate = vista.findViewById(R.id.evDate);
                spiColores = vista.findViewById(R.id.spiColors);

                spiDias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, dias));

                spiColores.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, colores));

                ibtnHora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora(evHour);
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( evName.getText().equals("") || evDescription.getText().equals("") || evHour.getText().toString().equals("") || spiDias.getSelectedItem().toString().equals("SELECCIONA UN DIA")) {
                            Toast.makeText(getContext(), "NO SE AGENDO TE FALTO LLENAR UN CAMPO.", Toast.LENGTH_SHORT).show();
                        } else {
                            Cita cita = new Cita();
                            cita.setIdCita(UUID.randomUUID().toString());
                            cita.setNomCliente(evName.getText().toString());
                            System.out.println(evName.getText().toString());            //AGREGA LOS CAMPOS A LA BASE DE DATOS
                            cita.setMotivo(evDescription.toString());

                            // databaseReference.child("Cita").child(cita.getIdCita()).setValue(cita);

                            Evento evento = new Evento();
                            evento.setIdEvent(UUID.randomUUID().toString());
                            evento.setEvName(evName.getText().toString());
                            evento.setEvDescription(evDescription.getText().toString());            //AGREGA LOS CAMPOS A LA BASE DE DATOS
                            evento.setEvHour(evHour.getText().toString());
                            evento.setEvDate(evDate.getText().toString());
                            evento.setEvColor(spiColores.getSelectedItem().toString());

                            databaseReference.child("Eventos").child(evento.getIdEvent()).setValue(evento);

                            }
                            Toast.makeText(getContext(), "Cita Agendada", Toast.LENGTH_SHORT).show();

                            obtenerEventos();
                        }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //adaptador.notifyDataSetChanged();
                builder.setCancelable(false);
                builder.show();
                builder.create();

            }
        });

        // ACCION BOTON DE MOSTRAR TODOS
        ibtnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MostrarTodos.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void obtenerHora(final TextView etHora) {
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                /*String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = " AM";
                } else {
                    AM_PM = " PM";
                }*/
                //Muestro la hora con el formato deseado
                //etHora.setText(horaFormateada + ":" + minutoFormateado + " " + AM_PM);
                etHora.setText(horaFormateada + ":" + minutoFormateado);
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);

        recogerHora.show();
    }

    // Metodo para obtener los eventos

    public void obtenerEventos(){
        databaseReference.child("Eventos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEventos.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Evento evento = objSnapshot.getValue(Evento.class);                              // GET DE EVENTOS
                    listaEventos.add(evento);

                    adaptador = new AdaptadorCitas(getContext(), null, listaEventos);
                    rvCitas.setAdapter(adaptador);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    /*public void obtenerCitas(String dia, List<Cita> citas) {
        citas.clear();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "dbSistema", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from citas WHERE dia = ? ORDER BY hora ASC", new String[] {dia});

        if(fila != null && fila.getCount() != 0) {
            fila.moveToFirst();
            do {
                citas.add(
                        new Cita(
                                fila.getString(0),
                                fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5),
                                fila.getString(6)
                        )
                );
            } while(fila.moveToNext());
        } else {
            //Toast.makeText(this, "No hay citas en este dia.", Toast.LENGTH_LONG).show();
        }

        db.close();

        //Toast.makeText(MainActivity.this, ""+citas.size(), Toast.LENGTH_SHORT).show();
  *//*      adaptador = new AdaptadorCitas(getContext(), citas,);
        rvCitas.setAdapter(adaptador);*//*
    }*/

    public void obtenerDiaActual() {
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        //Toast.makeText(getContext(), ""+dia, Toast.LENGTH_SHORT).show();

/*        switch (dia) {
            case 1:
                obtenerCitas("Domingo", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Domingo", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                obtenerCitas("Lunes", listaCitas);
                //Toast.makeText(getContext(), dia + ": Lunes", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                obtenerCitas("Martes", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Martes", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                obtenerCitas("Miercoles", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Miercoles", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                obtenerCitas("Jueves", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Jueves", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                obtenerCitas("Viernes", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Viernes", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                obtenerCitas("Sabado", listaCitas);
                //Toast.makeText(MainActivity.this, dia + ": Sabado", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }*/

    }
}