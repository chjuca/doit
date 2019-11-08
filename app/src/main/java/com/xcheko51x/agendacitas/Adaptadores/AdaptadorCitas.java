package com.xcheko51x.agendacitas.Adaptadores;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.Modelos.Cita;
import com.xcheko51x.agendacitas.Modelos.Evento;
import com.xcheko51x.agendacitas.R;

import java.util.Calendar;
import java.util.List;

public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.CitasViewHolder> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Context context;

    List<Evento> events;

    Evento eventoSelecionado;

    String[] dias = {"SELECCIONA UN DIA", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    String[] colores = {"GRIS", "VERDE", "NARANJA", "NEGRO", "PURPURA"};

    public AdaptadorCitas(Context context, List<Cita> listaCitas, List<Evento> listaEvents) {

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

        holder.evDate.setText(""+events.get(position).getEvDate());
        holder.evName.setText(events.get(position).getEvName());

        holder.evDescripcion.setText(events.get(position).getEvDescription());
        holder.evHour.setText(events.get(position).getEvHour());
        holder.evDate.setText(events.get(position).getEvDate());

        if(events.get(position).getEvColor().equals("GRIS")) {
            holder.clEvento.setBackgroundResource(R.color.gris);
        } else if(events.get(position).getEvColor().equals("VERDE")) {
            holder.clEvento.setBackgroundResource(R.color.verde);
        } else if(events.get(position).getEvColor().equals("NARANJA")) {
            holder.clEvento.setBackgroundResource(R.color.naranja);
        } else if(events.get(position).getEvColor().equals("NEGRO")) {
            holder.clEvento.setBackgroundResource(R.color.negro);
        } else if(events.get(position).getEvColor().equals("PURPURA")) {
            holder.clEvento.setBackgroundResource(R.color.purpura);
        }

        holder.ibtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);

                View vista = inflater.inflate(R.layout.dialog_agregar_cita, null);
                builder.setView(vista);

                final EditText evName, evDescripcion, evDate;
                final TextView evHour;
                ImageButton ibtnHora;
                final Spinner spiDias, spiColors;

                evName = vista.findViewById(R.id.evName);
                evDescripcion = vista.findViewById(R.id.evDescription);
                evDate = vista.findViewById(R.id.evDate);
                evHour = vista.findViewById(R.id.evHour);
                ibtnHora = vista.findViewById(R.id.ibtnHora);
                spiDias = vista.findViewById(R.id.spiDias);

                spiColors = vista.findViewById(R.id.spiColors);

                spiDias.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, dias));
                spiColors.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, colores));

                evName.setText(events.get(position).getEvName());
                evDescripcion.setText(events.get(position).getEvDescription());
                evHour.setText(events.get(position).getEvHour());
                evDate.setText(events.get(position).getEvDate());

                ibtnHora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora(evHour);
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( evName.getText().equals("") || evDescripcion.getText().equals("") || evHour.getText().toString().equals("") || spiDias.getSelectedItem().toString().equals("SELECCIONA UN DIA")) {
                            Toast.makeText(context, "NO SE AGENDO TE FALTO LLENAR UN CAMPO.", Toast.LENGTH_SHORT).show();
                        } else {

/*                          AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSistema", null, 1);
                            SQLiteDatabase db = admin.getWritableDatabase();

                            Cursor fila = db.rawQuery("select * from citas WHERE dia = ? AND hora = ?", new String[] {spiDias.getSelectedItem().toString(), evHour.getText().toString()});

                            if(fila != null && fila.getCount() != 0) {
                                Toast.makeText(context, "No se puede agendar en esa hora.", Toast.LENGTH_LONG).show();
                            } else {
                                ContentValues registro = new ContentValues();

                                registro.put("nomCliente", evName.getText().toString());
                                registro.put("telCliente", etTelefono.getText().toString());
                                registro.put("motivo", evDescripcion.getText().toString());
                                registro.put("hora", evHour.getText().toString());
                                registro.put("dia", spiDias.getSelectedItem().toString());
                                registro.put("color", spiColors.getSelectedItem().toString());

                                // los inserto en la base de datos
                                //db.update("citas", registro, "idCita=?", new String[]{""+citas.get(position).getIdCita()});

*//*                                citas.get(position).setNomCliente(etNombre.getText().toString());
                                citas.get(position).setTelCliente(etTelefono.getText().toString());
                                citas.get(position).setMotivo(etMotivo.getText().toString());
                                citas.get(position).setHoraCita(evHour.getText().toString());
                                citas.get(position).setDiaCita(spiDias.getSelectedItem().toString());
                                citas.get(position).setColor(spiColores.getSelectedItem().toString());*//*

                                notifyDataSetChanged();
                            }*/

                            // db.close();
                            Evento evento = new Evento();
                            evento.setIdEvent(events.get(position).getIdEvent());
                            evento.setEvName(evName.getText().toString().trim());
                            evento.setEvDescription(evDescripcion.getText().toString());
                            evento.setEvHour(evHour.getText().toString());
                            evento.setEvDate(evDate.getText().toString());
                            evento.setEvColor(spiColors.getSelectedItem().toString());

                            databaseReference.child("Eventos").child(evento.getIdEvent()).setValue(evento);


                            Toast.makeText(context, "Cita Modificada", Toast.LENGTH_SHORT).show();



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
                alerta.setMessage("¿Estas seguro que deseas eliminar la cita?");
                alerta.setCancelable(false);

                alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

 /*                       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSistema", null, 1);
                        SQLiteDatabase db = admin.getWritableDatabase();

                        int cant = db.delete("citas", "idCita=?", new String[] {""+events.get(position).getIdEvent()});
                        db.close();

                        if(cant == 1) {
                            Toast.makeText(context, "Cita eliminada", Toast.LENGTH_LONG).show();
                            events.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "No existe esa cita", Toast.LENGTH_LONG).show();
                        }*/
                        Evento evento = new Evento();
                        evento.setIdEvent(events.get(position).getIdEvent());
                        databaseReference.child("Eventos").child(evento.getIdEvent()).removeValue();

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
        TextView evDate, evName, evDescripcion, evHour;
        ImageButton ibtnModificar, ibtnBorrar;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);

            clEvento = itemView.findViewById(R.id.clEvento);
            evDate = itemView.findViewById(R.id.evDate);
            evName = itemView.findViewById(R.id.evNombre);
            evDescripcion = itemView.findViewById(R.id.evDescripcion);
            evHour = itemView.findViewById(R.id.evHour);
            ibtnModificar = itemView.findViewById(R.id.ibtnModificar);
            ibtnBorrar = itemView.findViewById(R.id.ibtnBorrar);

        }
    }
}
