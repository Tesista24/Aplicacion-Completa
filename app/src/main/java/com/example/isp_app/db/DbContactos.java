package com.example.isp_app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.example.isp_app.entidades.Contactos;
import java.util.ArrayList;

//Esta clase nos va a ayudar a hacer las transacciones a la tabla
public class DbContactos extends DbHelper{
    Context context;
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String apellido, String pin){

        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //funcion para insertar el registro:
            ContentValues values = new ContentValues();
            values.put("Nombre", nombre); //Insertando cada las columnas
            values.put("Apellido", apellido); //Insertando cada las columnas
            values.put("Pin", pin); //Insertando cada las columnas
            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    //Aqui empezaste a agregar::

    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos =null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

        if (cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setApellido(cursorContactos.getString(2));
                contacto.setPin(cursorContactos.getString(3));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }
}
