package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarSesion(View view){
        Intent intent = new Intent(this, InicioSesion.class);
        startActivity(intent);
    }

    public void Registrarse (View view) {
        Intent j = new Intent(this,Registro.class);
        startActivity(j);
    }
}


