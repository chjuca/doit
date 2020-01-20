package com.doitutpl.doit.Adaptadores;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doitutpl.doit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupViewHolder extends RecyclerView .ViewHolder{

    private TextView txtNombreGrupo;
    private LinearLayout linearLayout;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        txtNombreGrupo = itemView.findViewById(R.id.txtNombreGrupo);
        linearLayout = itemView.findViewById(R.id.cardGrupos);


    }

    public TextView getTxtNombreGrupo() {
        return txtNombreGrupo;
    }

    public void setTxtNombreGrupo(TextView txtNombreGrupo) {
        this.txtNombreGrupo = txtNombreGrupo;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}
