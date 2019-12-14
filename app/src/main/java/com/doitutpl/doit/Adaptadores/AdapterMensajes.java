package com.doitutpl.doit.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doitutpl.doit.Models.Mensaje;
import com.doitutpl.doit.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensajes>{

    private List<Mensaje> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes( Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){

    }

    @Override
    public HolderMensajes onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mesajes,parent,false);

        return new HolderMensajes(v);
    }

    @Override
    public void onBindViewHolder( HolderMensajes holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
