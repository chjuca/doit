package com.doitutpl.doit.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doitutpl.doit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderMensajes extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private ImageView fotoMensaje;
    private ImageView fileMensaje;

    public HolderMensajes(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.txtNombreMensaje);
        mensaje = (TextView) itemView.findViewById(R.id.txtMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotoMensaje = (ImageView) itemView.findViewById(R.id.fotoMensaje);
        fileMensaje = itemView.findViewById(R.id.fileMensaje);
    }


    public ImageView getFileMensaje() {
        return fileMensaje;
    }

    public void setFileMensaje(ImageView fileMensaje) {
        this.fileMensaje = fileMensaje;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }
}
