package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isp_app.db.DbContactos;
public class AgregarContactos extends AppCompatActivity {

    EditText txtNombreC, txtApellidoC, txtPinC;
    Button btnGuardarContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contactos);

        txtNombreC = findViewById(R.id.txtNombreC);
        txtApellidoC = findViewById(R.id.txtApellidoC);
        txtPinC = findViewById(R.id.txtPinC);
        btnGuardarContacto = findViewById(R.id.btnGuardarContacto);

        btnGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbContactos dbContactos = new DbContactos(AgregarContactos.this);
                long id = dbContactos.insertarContacto(txtNombreC.getText().toString(), txtApellidoC.getText().toString(), txtPinC.getText().toString());

                if (id > 0) {
                    Toast.makeText(AgregarContactos.this,"CONTACTO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(AgregarContactos.this,"HA OCURRIDO UN ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar(){
         txtNombreC.setText("");
         txtApellidoC.setText("");
         txtPinC.setText("");
    }

    public void aHome (View view){
        Intent intent = new Intent(AgregarContactos.this, Home.class);
        startActivity(intent);
    }


}