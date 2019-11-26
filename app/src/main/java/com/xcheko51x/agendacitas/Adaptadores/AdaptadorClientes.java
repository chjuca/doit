package com.xcheko51x.agendacitas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xcheko51x.agendacitas.Models.Cliente;
import com.xcheko51x.agendacitas.R;
import com.xcheko51x.agendacitas.TareasCliente;

import java.util.List;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ClientesViewHolder> {

    Context context;
    List<Cliente> listaClientes;

    public AdaptadorClientes(Context context, List<Cliente> listaClientes) {
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_clientes, null, false);
        return new AdaptadorClientes.ClientesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClientesViewHolder holder, final int position) {
        holder.tvNomCliente.setText(listaClientes.get(position).getNomCliente());
        holder.motivo.setText(listaClientes.get(position).getMotivo());

        holder.clCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TareasCliente.class);
                intent.putExtra("nomCliente", listaClientes.get(position).getNomCliente());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ClientesViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clCliente;
        TextView tvNomCliente, motivo;

        public ClientesViewHolder(@NonNull View itemView) {
            super(itemView);

            clCliente = itemView.findViewById(R.id.clCliente);
            tvNomCliente = itemView.findViewById(R.id.tvNomCliente);
            motivo = itemView.findViewById(R.id.motivo);
        }
    }
}
