package com.xcheko51x.agendacitas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.xcheko51x.agendacitas.Adaptadores.AdaptadorTodasCitas;
import com.xcheko51x.agendacitas.Modelos.Evento;

import java.util.ArrayList;
import java.util.List;

public class MostrarTodos extends AppCompatActivity {

    RecyclerView rvCitasLunes, rvCitasMartes, rvCitasMiercoles, rvCitasJueves, rvCitasViernes, rvCitasSabado, rvCitasDomingo;

/*    List<Cita> listaCitasLunes = new ArrayList<>();
    List<Cita> listaCitasMartes = new ArrayList<>();
    List<Cita> listaCitasMiercoles = new ArrayList<>();
    List<Cita> listaCitasJueves = new ArrayList<>();
    List<Cita> listaCitasViernes = new ArrayList<>();
    List<Cita> listaCitasSabado = new ArrayList<>();
    List<Cita> listaCitasDomingo = new ArrayList<>();*/

    AdaptadorTodasCitas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_todos);
        getSupportActionBar().hide();

        /*rvCitasLunes = findViewById(R.id.rvCitasLunes);
        rvCitasLunes.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Lunes", listaCitasLunes, rvCitasLunes);

        rvCitasMartes = findViewById(R.id.rvCitasMartes);
        rvCitasMartes.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Martes", listaCitasMartes, rvCitasMartes);

        rvCitasMiercoles = findViewById(R.id.rvCitasMiercoles);
        rvCitasMiercoles.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Miercoles", listaCitasMiercoles, rvCitasMiercoles);

        rvCitasJueves = findViewById(R.id.rvCitasJueves);
        rvCitasJueves.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Jueves", listaCitasJueves, rvCitasJueves);

        rvCitasViernes = findViewById(R.id.rvCitasViernes);
        rvCitasViernes.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Viernes", listaCitasViernes, rvCitasViernes);

        rvCitasSabado = findViewById(R.id.rvCitasSabado);
        rvCitasSabado.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Sabado", listaCitasSabado, rvCitasSabado);

        rvCitasDomingo = findViewById(R.id.rvCitasDomingo);
        rvCitasDomingo.setLayoutManager(new GridLayoutManager(MostrarTodos.this, 1));
        obtenerTodasCitas("Domingo", listaCitasDomingo, rvCitasDomingo);*/
    }

    public void obtenerTodasCitas(String dia, List<Evento> evento, RecyclerView rvCitas) {
/*        citas.clear();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MostrarTodos.this, "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from citas WHERE dia = ? ORDER BY hora ASC", new String[] {dia});

        if(fila != null && fila.getCount() != 0) {
            fila.moveToFirst();
            do {
                citas.add(
                        new Cita(
                                fila.getString(0),
                                fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5),
                                fila.getString(6)
                        )
                );
            } while(fila.moveToNext());
        } else {
            //Toast.makeText(MostrarTodos.this, "No hay citas en este dia.", Toast.LENGTH_LONG).show();
        }

        db.close();

        //Toast.makeText(MostrarTodos.this, ""+listaCitasLunes.size(), Toast.LENGTH_SHORT).show();
        adaptador = new AdaptadorTodasCitas(MostrarTodos.this, citas);
        rvCitas.setAdapter(adaptador);*/
    }
}
