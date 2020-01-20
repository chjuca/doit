package com.doitutpl.doit.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.doitutpl.doit.Models.Mensaje;
import com.doitutpl.doit.Models.MensajeRecibir;
import com.doitutpl.doit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensajes>{

    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context c;

    public AdapterMensajes( Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @Override
    public HolderMensajes onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mesajes,parent,false);

        return new HolderMensajes(v);
    }

    @Override
    public void onBindViewHolder( HolderMensajes holder, int position) {
        //System.out.println(listMensaje.isEmpty());

        //System.out.println(listMensaje.get(position).getMensaje());
        //System.out.println("---------------------------------");
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());

        //imagenens

        if (listMensaje.get(position).getType_mensaje().equals("2")) {
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        } else if (listMensaje.get(position).getType_mensaje().equals("1")) {
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

        Long codigoHora = listMensaje.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");//a pm o am
        holder.getHora().setText(sdf.format(d));

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

}
