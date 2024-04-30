package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    TextView nombreRecibido2, apellidoRecibido2, cedulaRecibida2, clave1Recibida2, preguntaRecibida2, respuestaRecibida2, pinRecibido2;
    String Nombre, Apellido, Cedula, Clave1, Respuesta,Pregunta, Pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreRecibido2 = findViewById(R.id.nombreRecibido2);
        apellidoRecibido2 = findViewById(R.id.apellidoRecibido2);
        cedulaRecibida2 = findViewById(R.id.cedulaRecibida2);
        clave1Recibida2 = findViewById(R.id.clave1Recibida2);
        preguntaRecibida2 = findViewById(R.id.preguntaRecibida2);
        respuestaRecibida2 = findViewById(R.id.respuestaRecibida2);
        pinRecibido2 = findViewById(R.id.pinRecibido2); //Tienes que borrarlo si al esp32 le llega cedula y clave cuando entras por IS

        //Recbiendo informacion de Home/Registro:
        Nombre = getIntent().getExtras().getString("nombreEnviado");
        Apellido = getIntent().getExtras().getString("apellidoEnviado");
        Cedula = getIntent().getExtras().getString("cedulaEnviada");
        Clave1 = getIntent().getExtras().getString("clave1Enviada");
        Respuesta = getIntent().getExtras().getString("respuestaEnviada");
        Pregunta = getIntent().getExtras().getString("preguntaEnviada");
        Pin = getIntent().getExtras().getString("pinEnviado");

        //Imprimiendo
        nombreRecibido2.setText(Nombre);
        apellidoRecibido2.setText(Apellido);
        cedulaRecibida2.setText(Cedula);
        clave1Recibida2.setText(Clave1);
        respuestaRecibida2.setText(Respuesta);
        preguntaRecibida2.setText(Pregunta);
        pinRecibido2.setText(Pin);

    }
    public void aHome (View view){
        Intent intent = new Intent(Perfil.this, Home.class);
        startActivity(intent);
    }
}
