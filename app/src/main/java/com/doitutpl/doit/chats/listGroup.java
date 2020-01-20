package com.doitutpl.doit.chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doitutpl.doit.Adaptadores.GroupViewHolder;
import com.doitutpl.doit.Models.Chats;
import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.R;
import com.doitutpl.doit.StaticData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class listGroup extends AppCompatActivity {

    private RecyclerView rvGruposL;

    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grupos);
        rvGruposL = findViewById(R.id.rvGruposL);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvGruposL.setLayoutManager(linearLayoutManager);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Groups");


        FirebaseRecyclerOptions<Group> options =
                new FirebaseRecyclerOptions.Builder<Group>()
                        .setQuery(query, Group.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Group, GroupViewHolder>(options) {
            @Override
            public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_group, parent, false);

                return new GroupViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(GroupViewHolder holder, int position, final Group model) {

                holder.getTxtNombreGrupo().setText(model.nameGroup);

                final Group lGrupo = new Group(getSnapshots().getSnapshot(position).getKey(), model);

                holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(listGroup.this, chat.class);
                        intent.putExtra("KeyGroup", lGrupo.getKeyGroup());
                        StaticData.currentsKeyChat = lGrupo.getKeyGroup();
                        startActivity(intent);
                    }
                });
            }
        };
        rvGruposL.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
