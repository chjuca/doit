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

import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.Modelos.Cita;
import com.xcheko51x.agendacitas.Modelos.Evento;
import com.xcheko51x.agendacitas.R;

import java.util.Calendar;
import java.util.List;

public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.CitasViewHolder> {

    Context context;
    List<Cita> citas;

    List<Evento> events;

    String[] dias = {"SELECCIONA UN DIA", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
    String[] colores = {"GRIS", "VERDE", "NARANJA", "NEGRO", "PURPURA"};

    public AdaptadorCitas(Context context, List<Cita> listaCitas, List<Evento> listaEvents) {

        this.context = context;
        this.citas = listaCitas;
        this.events = listaEvents;

        if(events.size() == 0) {
            Toast.makeText(context, "NO HAY EVENTOS ESTE DIA", Toast.LENGTH_SHORT).show();
        }
    }



    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_citas, null, false);
        return new AdaptadorCitas.CitasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, final int position) {

        holder.tvIdCita.setText(""+citas.get(position).getIdCita());
        holder.tvNombre.setText(citas.get(position).getNomCliente());
        holder.tvTelefono.setText(citas.get(position).getTelCliente());

        holder.tvMotivo.setText(citas.get(position).getMotivo());
        holder.tvHora.setText(citas.get(position).getHoraCita());
        holder.tvDia.setText(citas.get(position).getDiaCita());

        if(citas.get(position).getColor().equals("GRIS")) {
            holder.clCita.setBackgroundResource(R.color.gris);
        } else if(citas.get(position).getColor().equals("VERDE")) {
            holder.clCita.setBackgroundResource(R.color.verde);
        } else if(citas.get(position).getColor().equals("NARANJA")) {
            holder.clCita.setBackgroundResource(R.color.naranja);
        } else if(citas.get(position).getColor().equals("NEGRO")) {
            holder.clCita.setBackgroundResource(R.color.negro);
        } else if(citas.get(position).getColor().equals("PURPURA")) {
            holder.clCita.setBackgroundResource(R.color.purpura);
        }

        holder.ibtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);

                View vista = inflater.inflate(R.layout.dialog_agregar_cita, null);
                builder.setView(vista);

                final EditText etNombre, etTelefono, etMotivo;
                final TextView tvHora;
                ImageButton ibtnHora;
                final Spinner spiDias, spiColores;

                etNombre = vista.findViewById(R.id.evName);
                etTelefono = vista.findViewById(R.id.etTelefono);
                etMotivo = vista.findViewById(R.id.evDescription);
                tvHora = vista.findViewById(R.id.evHour);
                ibtnHora = vista.findViewById(R.id.ibtnHora);
                spiDias = vista.findViewById(R.id.spiDias);
                spiColores = vista.findViewById(R.id.spiColors);

                spiDias.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, dias));
                spiColores.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, colores));

                etNombre.setText(citas.get(position).getNomCliente());
                etTelefono.setText(citas.get(position).getTelCliente());
                etMotivo.setText(citas.get(position).getMotivo());
                tvHora.setText(citas.get(position).getHoraCita());

                ibtnHora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obtenerHora(tvHora);
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( etNombre.getText().equals("") || etTelefono.getText().equals("") || etMotivo.getText().equals("") || tvHora.getText().toString().equals("") || spiDias.getSelectedItem().toString().equals("SELECCIONA UN DIA")) {
                            Toast.makeText(context, "NO SE AGENDO TE FALTO LLENAR UN CAMPO.", Toast.LENGTH_SHORT).show();
                        } else {

                            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSistema", null, 1);
                            SQLiteDatabase db = admin.getWritableDatabase();

                            Cursor fila = db.rawQuery("select * from citas WHERE dia = ? AND hora = ?", new String[] {spiDias.getSelectedItem().toString(), tvHora.getText().toString()});

                            if(fila != null && fila.getCount() != 0) {
                                Toast.makeText(context, "No se puede agendar en esa hora.", Toast.LENGTH_LONG).show();
                            } else {
                                ContentValues registro = new ContentValues();

                                registro.put("nomCliente", etNombre.getText().toString());
                                registro.put("telCliente", etTelefono.getText().toString());
                                registro.put("motivo", etMotivo.getText().toString());
                                registro.put("hora", tvHora.getText().toString());
                                registro.put("dia", spiDias.getSelectedItem().toString());
                                registro.put("color", spiColores.getSelectedItem().toString());

                                // los inserto en la base de datos
                                db.update("citas", registro, "idCita=?", new String[]{""+citas.get(position).getIdCita()});

                                citas.get(position).setNomCliente(etNombre.getText().toString());
                                citas.get(position).setTelCliente(etTelefono.getText().toString());
                                citas.get(position).setMotivo(etMotivo.getText().toString());
                                citas.get(position).setHoraCita(tvHora.getText().toString());
                                citas.get(position).setDiaCita(spiDias.getSelectedItem().toString());
                                citas.get(position).setColor(spiColores.getSelectedItem().toString());

                                notifyDataSetChanged();
                            }

                            db.close();

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

                        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSistema", null, 1);
                        SQLiteDatabase db = admin.getWritableDatabase();

                        int cant = db.delete("citas", "idCita=?", new String[] {""+citas.get(position).getIdCita()});
                        db.close();

                        if(cant == 1) {
                            Toast.makeText(context, "Cita eliminada", Toast.LENGTH_LONG).show();
                            citas.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "No existe esa cita", Toast.LENGTH_LONG).show();
                        }
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
        return citas.size();
    }

    public class CitasViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clCita;
        TextView tvIdCita, tvNombre, tvTelefono, tvMotivo, tvHora, tvDia;
        ImageButton ibtnModificar, ibtnBorrar;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);

            clCita = itemView.findViewById(R.id.clCita);
            tvIdCita = itemView.findViewById(R.id.tvIdCita);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvMotivo = itemView.findViewById(R.id.tvMotivo);
            tvHora = itemView.findViewById(R.id.evHour);
            tvDia = itemView.findViewById(R.id.tvDia);
            ibtnModificar = itemView.findViewById(R.id.ibtnModificar);
            ibtnBorrar = itemView.findViewById(R.id.ibtnBorrar);

        }
    }
}
