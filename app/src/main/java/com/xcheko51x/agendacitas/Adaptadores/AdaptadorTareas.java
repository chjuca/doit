package com.xcheko51x.agendacitas.Adaptadores;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.xcheko51x.agendacitas.AdminSQLiteOpenHelper;
import com.xcheko51x.agendacitas.Modelos.Tarea;
import com.xcheko51x.agendacitas.R;

import java.util.List;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.TareasViewHolder> {

    Context context;
    List<Tarea> listaTareas;

    String[] estados = {"PENDIENTE", "ENVIADO", "ENTREGADO"};

    public AdaptadorTareas(Context context, List<Tarea> listaTareas) {
        this.context = context;
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_tareas, null, false);
        return new AdaptadorTareas.TareasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, final int position) {

        holder.tvDescTarea.setText(listaTareas.get(position).getDescTarea());
        holder.tvEstado.setText(listaTareas.get(position).getEstado());

        holder.ibtnEditarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View vista = inflater.inflate(R.layout.dialog_tarea, null);
                builder.setView(vista);

                final TextView tvAccion = vista.findViewById(R.id.tvAccion);
                final EditText etDescTarea = vista.findViewById(R.id.etDescTarea);
                final Spinner spiEstado = vista.findViewById(R.id.spiEstado);
                final ImageButton ibtnLimpiarTexto = vista.findViewById(R.id.ibtnLimpiarTexto);

                tvAccion.setText("EDITAR TARERA");

                spiEstado.setAdapter(new ArrayAdapter<String>(context, R.layout.item_spinner, estados));

                for(int i = 0 ; i < estados.length ; i++) {
                    if(estados[i].equals(listaTareas.get(position).getEstado())) {
                        spiEstado.setSelection(i);
                    }
                }

                etDescTarea.setText(listaTareas.get(position).getDescTarea());

                ibtnLimpiarTexto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etDescTarea.setText("");
                    }
                });

                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(etDescTarea.getText().toString().trim().equals("")) {
                            Toast.makeText(context, "Se debe de llenar el campo y seleccionar una opción", Toast.LENGTH_SHORT).show();
                        } else {
                            actualizarTarea(listaTareas.get(position).getIdTarea(), etDescTarea, spiEstado.getSelectedItem().toString());

                            listaTareas.get(position).setDescTarea(etDescTarea.getText().toString());
                            listaTareas.get(position).setEstado(spiEstado.getSelectedItem().toString());

                            notifyDataSetChanged();
                        }
                    }
                });

                builder.setCancelable(true);
                builder.create().show();
            }
        });
    }

    public void actualizarTarea(int idTarea, EditText etDescTarea, String spiEstado) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from citas WHERE dia = ? AND hora = ?", new String[] {""+idTarea});

        if(fila != null && fila.getCount() != 0) {
            Toast.makeText(context, "No se puede agendar en esa hora.", Toast.LENGTH_LONG).show();
        } else {
            ContentValues registro = new ContentValues();

            registro.put("descTarea", etDescTarea.getText().toString());
            registro.put("estado", spiEstado);

            // los inserto en la base de datos
            db.update("tareas", registro, "idTarea=?", new String[]{"" + idTarea});

            db.close();
        }
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public class TareasViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescTarea, tvEstado;
        ImageButton ibtnEditarEstado;

        public TareasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescTarea = itemView.findViewById(R.id.tvDescTarea);
            tvEstado = itemView.findViewById(R.id.motivo);
            ibtnEditarEstado = itemView.findViewById(R.id.ibtnEditarEstado);
        }
    }
}
