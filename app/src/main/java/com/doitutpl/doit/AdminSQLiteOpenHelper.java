package com.doitutpl.doit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // TABLA CITAS
        db.execSQL("CREATE TABLE citas(" +
                "idCita INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomCliente TEXT," +
                "telCliente TEXT," +
                "motivo TEXT," +
                "hora TEXT," +
                "dia TEXT," +
                "color TEXT" +
                ")");

        // TABLA TAREAS
        db.execSQL("CREATE TABLE tareas(" +
                "idTarea INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomCliente TEXT," +
                "descTarea TEXT," +
                "estado TEXT" +
                ")");

        /*
        * citas -> COLOR
        * En este campo se los colores son los que se encuentran en el archivo colores.xml
        * de res->values->colores.xml
        *
        * tareas -> ESTADO
        * En este campo se admiten los siguientes tipos de registro.
        * entregado
        * pendiente
        * enviado
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void borrarRegistros(String tabla, SQLiteDatabase db) {
        db.execSQL("DELETE FROM "+tabla);
    }
}
