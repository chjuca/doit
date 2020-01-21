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
    private ImageView fotoMensaje2;
    private ImageView fileMensaje2;

    public HolderMensajes(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.txtNombreMensaje);
        mensaje = (TextView) itemView.findViewById(R.id.txtMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotoMensaje = (ImageView) itemView.findViewById(R.id.fotoMensaje);
        fileMensaje = itemView.findViewById(R.id.fileMensaje);
        fotoMensaje2 = (ImageView) itemView.findViewById(R.id.fotoMensaje2);
        fileMensaje2 = itemView.findViewById(R.id.fileMensaje2);
    }

    public ImageView getFotoMensaje2() {
        return fotoMensaje2;
    }

    public void setFotoMensaje2(ImageView fotoMensaje2) {
        this.fotoMensaje2 = fotoMensaje2;
    }

    public ImageView getFileMensaje2() {
        return fileMensaje2;
    }

    public void setFileMensaje2(ImageView fileMensaje2) {
        this.fileMensaje2 = fileMensaje2;
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
