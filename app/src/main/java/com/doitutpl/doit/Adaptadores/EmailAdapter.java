package com.doitutpl.doit.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doitutpl.doit.Models.Email;
import com.doitutpl.doit.Models.Events;
import com.doitutpl.doit.R;
import com.doitutpl.doit.ui.CreateGroup;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {
    private ArrayList<Email> mDataset;
    CreateGroup createGroup = new CreateGroup();
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Aqui agregar el icono de persona
        public TextView userEmail;
        public ImageButton userDelete;

        public ViewHolder(View v) {
            super(v);
            userEmail = v.findViewById(R.id.userEmail);
            userDelete = v.findViewById(R.id.userDelete);
        }

    }

    public EmailAdapter(ArrayList<Email> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    public EmailAdapter(ArrayList<Email> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EmailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_email, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.userEmail.setText(mDataset.get(position).getUserEmail());



        holder.userDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(context);

                    alerta.setTitle("ELIMINAR");
                    alerta.setMessage("¿Estas seguro que deseas eliminar este correo?");
                    alerta.setCancelable(false);

                    alerta.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            System.out.println("posicion: " + position);
                            mDataset.remove(position);
                            notifyDataSetChanged();
                                for (Email email: mDataset){
                                System.out.println(email.getUserEmail());
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
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
