package com.doitutpl.doit.ui.citas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.doitutpl.doit.Controllers.GroupsController;
import com.doitutpl.doit.Models.EvGroup;
import com.doitutpl.doit.Models.Group;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.doitutpl.doit.Adaptadores.AdaptadorCitas;
import com.doitutpl.doit.Models.EvDate;
import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.R;
import com.doitutpl.doit.pruebaCalendario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class CitasFragment extends Fragment {

    GroupsController ObjGroupsController = new GroupsController();
    ArrayList<Group> groupList = new ArrayList<>();

    ImageButton ibtnAdd, ibtnShowAll;
    RecyclerView rvCitas;
    EditText evName, evDescription;
    TextView evHour,evDate;
    ImageButton ibtnHora, ibtnDate;
    Spinner spiGroups ,spiPriority;
    Switch isPublic;
    private int dia, mes, anio;
    String email = " ";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    AdaptadorCitas adaptador;

    List<Events> listaEvents = new ArrayList<>();

    EvGroup evGroup = new EvGroup();

    String[] dias = {"SELECCIONA UN DIA", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    String[] priority = {"ALTA", "MEDIA", "BAJA"};
    boolean eventIsPublic = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_citas, container, false);

        groupList = ObjGroupsController.pullUserGroups(getContext());

        ibtnAdd = root.findViewById(R.id.ibtnAgregar);
        ibtnShowAll = root.findViewById(R.id.ibtnMostrarTodas);
        //spiDiasMain = root.findViewById(R.id.spiDiasMain);
        rvCitas = root.findViewById(R.id.rvCitas);
        rvCitas.setLayoutManager(new GridLayoutManager(getContext(), 1));

        obtenerDiaActual();
        inicializarFirebase();
        obtenerEventos();

        ibtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();

                View vista = inflater.inflate(R.layout.dialog_agregar_cita, null);
                builder.setView(vista);

                evName = vista.findViewById(R.id.groupKey);
                evDescription = vista.findViewById(R.id.groupPass);
                evHour = vista.findViewById(R.id.evHour);
                ibtnHora = vista.findViewById(R.id.ibtnHora);
                ibtnDate = vista.findViewById(R.id.btnCopy);
                evDate = vista.findViewById(R.id.evDate);
                spiGroups = vista.findViewById(R.id.spiGroups);
                spiGroups.setEnabled(false);
                isPublic = vista.findViewById(R.id.isPublic);


                ibtnDate.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        dia = calendar.get(Calendar.DAY_OF_MONTH);
                        mes = calendar.get(Calendar.MONTH);
                        anio = calendar.get(Calendar.YEAR);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                evDate.setText(dayOfMonth + "/" + (month+1) + "/" + year );
                            }
                        },anio, mes, dia);
                        datePickerDialog.show();
                    }
                });
                spiPriority = vista.findViewById(R.id.spiColors);

                spiPriority.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, priority));

                isPublic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isPublic.isChecked()){
                            spiGroups.setEnabled(true);
                            eventIsPublic = true;
                            final String[] groupsName = new String[groupList.size()];
                            for (int i = 0; i < groupsName.length; i++){
                                groupsName[i]= groupList.get(i).getNameGroup().toUpperCase();
                            }
                            spiGroups.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, groupsName));

                        }else{
                            spiGroups.setEnabled(false);
                            eventIsPublic = false;
                        }
                    }
                });


                ibtnHora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora(evHour);
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( evName.getText().equals("") || evDescription.getText().equals("") || evHour.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "NO SE AGENDO TE FALTO LLENAR UN CAMPO.", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                Events events = new Events();
                                events.setIdEvent(UUID.randomUUID().toString());
                                String[] dateParts = evDate.getText().toString().split("/");
                                String[] hourParts = evHour.getText().toString().split(":");
                                EvDate evDate = new EvDate();


                                DecimalFormat format = new DecimalFormat("00");
                                evDate.setDay(format.format(Integer.parseInt(dateParts[0])));
                                evDate.setMonth(format.format(Integer.parseInt(dateParts[1])));
                                evDate.setYear(Integer.parseInt(dateParts[2]));
                                evDate.setHours(format.format(Integer.parseInt(hourParts[0])));
                                evDate.setMinutes(format.format(Integer.parseInt(hourParts[1])));
                                int priority;
                                if(spiPriority.getSelectedItem().toString() == "ALTA"){
                                    priority = 1; }else if(spiPriority.getSelectedItem().toString() =="MEDIA"){
                                    priority = 2 ; }else{
                                    priority = 3; }
                                if (user != null) {
                                    email = user.getEmail();
                                }

                                events.setEvName(evName.getText().toString());
                                events.setEvDescription(evDescription.getText().toString());            //AGREGA LOS CAMPOS A LA BASE DE DATOS
                                events.setEvDate(evDate);
                                events.setEvPriority(priority);
                                events.setEvCreatorUser(email);
                                events.setPublic(eventIsPublic);
                                events.setEvGroups(null);
                                events.setPublic(false);                        //***  MODIFICAR SI CAMBIAMOS LA PANTALLA     ***
                                events.setState(1);


                                if (eventIsPublic){
                                    events.setPublic(true);
                                    events.setEvGroups(groupList.get(spiGroups.getSelectedItemPosition()));
                                    evGroup.setKeyGroup(events.getIdEvent());
                                    evGroup.setName(events.getEvName());
                                }

                                databaseReference.child("Events").child(events.getIdEvent()).setValue(events);          // Guardamos el Evento
                                databaseReference.child("EvGroups").child(events.getIdEvent()).setValue(evGroup);          // Guardamos el Evento Grupal

                                Toast.makeText(getContext(), "Evento Agendado", Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Por favor, Intenta de nuevo", Toast.LENGTH_SHORT).show();
                            }
                            }
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
                Intent intent = new Intent(getContext(), pruebaCalendario.class);
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

        databaseReference.child("Events").orderByChild("evCreatorUser").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEvents.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()) {

                    Events events = objSnapshot.getValue(Events.class);                              // GET DE EVENTOS
                    listaEvents.add(events);

                    adaptador = new AdaptadorCitas(getContext(), listaEvents);
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