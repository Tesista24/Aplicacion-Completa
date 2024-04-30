package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.isp_app.adaptadores.ListaContactosAdapter;
import com.example.isp_app.db.DbContactos;
import com.example.isp_app.entidades.Contactos;

import java.util.ArrayList;

public class contactos extends AppCompatActivity {

    TextView txt;
    Button btnAgregar, btnHome;

    /*Esto es nuevo*/
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    /*Esto es nuevo*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        //txt = findViewById(R.id.txtEstatus);
        btnAgregar = findViewById(R.id.btnAgregar);
        listaContactos = findViewById(R.id.listaContactos);
        //btnHome = findViewById(R.id.btnAHome);

        /*Esto es nuevo*/
        listaContactos.setLayoutManager(new LinearLayoutManager(this));
        DbContactos dbContactos = new DbContactos(contactos.this);

        listaArrayContactos = new ArrayList<>();

        ListaContactosAdapter adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        //crearBaseDeDatos(); NO SE ESTA EJECUTANDO, cualquier error quitale el comentario

    }

    /*public void crearBaseDeDatos(){
        DbHelper dbHelper = new DbHelper(contactos.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null){
            txt.setText("BASE DE DATOS CREADA");
        } else {
            txt.setText("ERROR AL CREAR LA BASE DE DATOS");
        }
    }*/

    public void guardarContacto(View view){
        Intent i = new Intent(this, AgregarContactos.class);
        startActivity(i);
    }

    public void aHome (View view){
        Intent intent = new Intent(contactos.this, Home.class);
        startActivity(intent);
    }

}