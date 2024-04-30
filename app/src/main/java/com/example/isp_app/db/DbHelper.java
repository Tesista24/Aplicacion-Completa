package com.example.isp_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "agenda.db";
    public static final String TABLE_CONTACTOS = "t_contactos";
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Aqui creamos la tabla:
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_CONTACTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL, " +
                "pin TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Este metodo se ejecuta cuando cambie la version de la base de datos
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_CONTACTOS);
        onCreate(sqLiteDatabase);
    }
}
