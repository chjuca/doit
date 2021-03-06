package com.doitutpl.doit.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.R;

import java.util.List;

public class AdaptadorTodasCitas extends RecyclerView.Adapter<AdaptadorTodasCitas.TodasCitasViewHolder> {

    Context context;
    // List<Cita> citas;

    public AdaptadorTodasCitas(Context context, List<Events> events) {
        this.context = context;
        //this.citas = citas;

    }

    @NonNull
    @Override
    public TodasCitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_todas_citas, null, false);
        return new AdaptadorTodasCitas.TodasCitasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull TodasCitasViewHolder holder, int position) {

/*        holder.tvIdCita.setText(""+citas.get(position).getIdCita());
        holder.tvHora.setText(citas.get(position).getHoraCita());
        holder.tvNombre.setText(citas.get(position).getNomCliente());
        holder.tvTelefono.setText(citas.get(position).getTelCliente());
        holder.tvMotivo.setText(citas.get(position).getMotivo());

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
        }*/

    }

    @Override
    public int getItemCount() {
        //return citas.size();
        return 1;
    }

    public class TodasCitasViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clCita;
        TextView tvIdCita, tvHora, tvNombre, tvTelefono, tvMotivo;

        public TodasCitasViewHolder(@NonNull View itemView) {
            super(itemView);

            clCita = itemView.findViewById(R.id.clEvento);
            tvIdCita = itemView.findViewById(R.id.groupKey);
            tvHora = itemView.findViewById(R.id.evHour);
            tvNombre = itemView.findViewById(R.id.evNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvMotivo = itemView.findViewById(R.id.evDescripcion);

        }
    }
}
