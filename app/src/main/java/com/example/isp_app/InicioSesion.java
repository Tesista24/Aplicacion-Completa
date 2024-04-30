package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Random;

public class InicioSesion extends AppCompatActivity {
    Button btnIniciarSesion;
    EditText txtClave, txtCedula;
    String enviarDatosBT = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtClave = findViewById(R.id.txtClave);
        txtCedula = findViewById(R.id.txtCedula);

        Toast.makeText(this, "Encienda el BLUETOOTH de su telefono", Toast.LENGTH_SHORT).show();
    }
    public void iniciarSerion(View view) {
        if (!TextUtils.isEmpty(txtCedula.getText().toString())) {
            // El EditText no está vacío
            if (!TextUtils.isEmpty(txtClave.getText().toString())) {
                Intent k = new Intent(InicioSesion.this, Home.class);
                //k.putExtra("pinEnviado",txtUsuario.getText().toString()); //Se envia el pin
                k.putExtra("cedulaEnviada",txtCedula.getText().toString());
                k.putExtra("clave1Enviada",txtClave.getText().toString()); //Se envia el apellido
                k.putExtra("BalizaEnvio",enviarDatosBT);
                startActivity(k);
            } else {
                Toast.makeText(InicioSesion.this, "LA CONTRASEÑA ES UN CAMPO OBLIGATORIO", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El EditText está vacío
            Toast.makeText(InicioSesion.this, "LA CEDULA ES UN CAMPO OBLIGATORIO", Toast.LENGTH_SHORT).show();
        }

    }


}




