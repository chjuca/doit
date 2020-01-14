package com.doitutpl.doit.Adaptadores;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.doitutpl.doit.Models.EvGroup;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.doitutpl.doit.Models.EvDate;
import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.CitasViewHolder> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int dia, mes, anio;

    Context context;

    List<Events> events;

    Events eventsSelecionado;

    String[] priority = {"ALTA", "MEDIA", "BAJA"};

    public AdaptadorCitas(Context context, List<Events> listaEvents) {

        this.context = context;
        this.events = listaEvents;

        if(events.size() == 0) {
            Toast.makeText(context, "NO HAY EVENTOS ESTE DIA", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inicializarFirebase();
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_citas, null, false);
        return new AdaptadorCitas.CitasViewHolder(vista);
    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, final int position) {

        holder.evDate.setText(events.get(position).getEvDate().getDay()+"/"+events.get(position).getEvDate().getMonth()+"/"+events.get(position).getEvDate().getYear());
        holder.evName.setText(events.get(position).getEvName());

        holder.evDescripcion.setText(events.get(position).getEvDescription());
        holder.evHour.setText(events.get(position).getEvDate().getHours()+":"+events.get(position).getEvDate().getMinutes());

        if(events.get(position).getEvPriority()==1){
            holder.clEvento.setBackgroundResource(R.color.high);
        }else if(events.get(position).getEvPriority()==2){
            holder.clEvento.setBackgroundResource(R.color.half);
            }else{
                holder.clEvento.setBackgroundResource(R.color.low);
        }

        holder.ibtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);

                View vista = inflater.inflate(R.layout.dialog_agregar_cita, null);
                builder.setView(vista);

                final EditText evName, evDescripcion;
                final TextView evHour, evDate;
                ImageButton ibtnHora, ibtnDate;
                final Spinner spiPriority, spiGroups;
                final Switch isPublic;

                evName = vista.findViewById(R.id.groupKey);
                evDescripcion = vista.findViewById(R.id.groupPass);
                evDate = vista.findViewById(R.id.evDate);
                evHour = vista.findViewById(R.id.evHour);
                ibtnHora = vista.findViewById(R.id.ibtnHora);
                ibtnDate = vista.findViewById(R.id.btnCopy);
                isPublic = vista.findViewById(R.id.isPublic);
                isPublic.setEnabled(false);
                spiGroups = vista.findViewById(R.id.spiGroups);
                spiGroups.setEnabled(false);
                ibtnDate.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        dia = calendar.get(Calendar.DAY_OF_MONTH);
                        mes = calendar.get(Calendar.MONTH);
                        anio = calendar.get(Calendar.YEAR);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                evDate.setText(dayOfMonth + "/" + (month+1) + "/" + year );
                            }
                        },dia, mes, anio);
                        datePickerDialog.show();
                    }
                });



                spiPriority = vista.findViewById(R.id.spiColors);

                spiPriority.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, priority));

                evName.setText(events.get(position).getEvName());
                evDescripcion.setText(events.get(position).getEvDescription());
                evHour.setText(events.get(position).getEvDate().getHours()+":"+events.get(position).getEvDate().getMinutes());
                evDate.setText(events.get(position).getEvDate().getDay()+"/"+events.get(position).getEvDate().getMonth()+"/"+events.get(position).getEvDate().getYear());

                if (events.get(position).isPublic()) {
                    isPublic.setChecked(events.get(position).isPublic());
                    isPublic.setLinkTextColor(Color.BLUE);
                }else {
                    //=====================
                    // AQUI PONER EL ELSE
                    //=================
                }

                ibtnHora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora(evHour);
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( evName.getText().equals("") || evDescripcion.getText().equals("") || evHour.getText().toString().equals("")) {
                            Toast.makeText(context, "NO SE AGENDO TE FALTO LLENAR UN CAMPO.", Toast.LENGTH_SHORT).show();
                        } else {
                            Events events = new Events();
                            EvDate evdate = new EvDate();
                            String[] dateParts = evDate.getText().toString().split("/");
                            String[] hourParts = evHour.getText().toString().split(":");

                            DecimalFormat format = new DecimalFormat("00");

                            evdate.setDay(format.format(Integer.parseInt(dateParts[0])));
                            evdate.setMonth(format.format(Integer.parseInt(dateParts[1])));
                            evdate.setYear(Integer.parseInt(dateParts[2]));
                            evdate.setHours(format.format(Integer.parseInt(hourParts[0])));
                            evdate.setMinutes(format.format(Integer.parseInt(hourParts[1])));
                            int priority;
                            if(spiPriority.getSelectedItem().toString() == "ALTA"){
                                priority = 1; }else if(spiPriority.getSelectedItem().toString() =="MEDIA"){
                                priority = 2 ; }else{
                                priority = 3; }

                            events.setIdEvent(AdaptadorCitas.this.events.get(position).getIdEvent());
                            events.setEvName(evName.getText().toString().trim());
                            events.setEvDescription(evDescripcion.getText().toString());            //*** MODIFICAR ALGUN EVENTO  ****
                            events.setEvDate(evdate);
                            events.setEvPriority(priority);
                            events.setEvCreatorUser(AdaptadorCitas.this.events.get(position).getEvCreatorUser());
                            events.setPublic(false);                            //***  MODIFICAR SI CAMBIAMOS LA PANTALLA     ***
                            events.setEvGroups(null);
                            events.setState(1);

                            databaseReference.child("Events").child(events.getIdEvent()).setValue(events);


                            Toast.makeText(context, "Evento Modificado", Toast.LENGTH_SHORT).show();



                        }
                    }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setCancelable(false);
                builder.show();
                builder.create();

            }
        });

        holder.ibtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(context);

                alerta.setTitle("ELIMINAR");
                alerta.setMessage("¿Estas seguro que deseas eliminar el Evento?");
                alerta.setCancelable(false);

                alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Events events = new Events();
                        events.setIdEvent(AdaptadorCitas.this.events.get(position).getIdEvent());
                        databaseReference.child("Events").child(events.getIdEvent()).removeValue();
                        databaseReference.child("EvGroups").child(events.getIdEvent()).removeValue();

                    }
                });

                alerta.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }
        });
    }

    public void obtenerHora(final TextView etHora) {
        TimePickerDialog recogerHora = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class CitasViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clEvento;
        TextView evDate, evName, evDescripcion, evHour;         //cambio para realizar el commit nuevamenterrar;
        ImageButton ibtnModificar, ibtnBorrar;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);

            clEvento = itemView.findViewById(R.id.clEvento);
            evDate = itemView.findViewById(R.id.groupKey);
            evName = itemView.findViewById(R.id.evNombre);
            evDescripcion = itemView.findViewById(R.id.evDescripcion);
            evHour = itemView.findViewById(R.id.evHour);
            ibtnModificar = itemView.findViewById(R.id.ibtnModificar);
            ibtnBorrar = itemView.findViewById(R.id.ibtnBorrar);

        }
    }
}
