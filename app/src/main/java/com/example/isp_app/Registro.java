package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Registro extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtCedula, txtClave1, txtClave2, txtRespuesta;
    Spinner preguntasSeguras;
    String item;
    String clave1, clave2, Cedula, enviarDatosBT = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCedula = findViewById(R.id.txtCedula);
        txtClave1 = findViewById(R.id.txtClave1);
        txtClave2 = findViewById(R.id.txtClave2);
        txtRespuesta = findViewById(R.id.txtRespuesta);
        preguntasSeguras = findViewById(R.id.preguntasSeguras);
        Spinner spinner = findViewById(R.id.preguntasSeguras);



        //Tratamiento del Spinner:
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                item = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(Registro.this, "Pregunta elegida: "+item,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Nombre de la primera mascota");
        arrayList.add("Ciudad donde nació su madre");
        arrayList.add("Nombre de su abuela paterna");
        arrayList.add("País que siempre ha querido conocer");
        arrayList.add("Último libro que leyó");
        arrayList.add("Comida favorita");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        //Tratamiento del Spinner


        Toast.makeText(this, "Antes de registrarse vincule los dispositivos BLUETOOTH indicados", Toast.LENGTH_LONG).show();
    }

    public void registrar(View view) {

        clave1 = txtClave1.getText().toString();
        clave2 = txtClave2.getText().toString();

        if (!TextUtils.isEmpty(txtNombre.getText().toString())) {

            if (!TextUtils.isEmpty(txtApellido.getText().toString())) {

                if (!TextUtils.isEmpty(txtCedula.getText().toString())) {

                    if (!TextUtils.isEmpty(txtClave1.getText().toString())) {

                        if (TextUtils.isEmpty(txtClave2.getText().toString())){
                            // El EditText está vacío
                            Toast.makeText(Registro.this, "DEBE CONFIRMAR LA CONTRASEÑA", Toast.LENGTH_SHORT).show();

                        } else {
                            if (clave1.equals(clave2)) {
                                //Las claves coinciden
                                if (!TextUtils.isEmpty(txtRespuesta.getText().toString())) {
                                    //Toast.makeText(Registro.this, "USTED HA SIDO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                                    //Generacion del PIN...............................................................................
                                    // Convirtiendo los editTexts a un string
                                    String textoN = txtNombre.getText().toString();
                                    String textoA = txtApellido.getText().toString();
                                    // Convirtiendo ahora los string a arreglos
                                    char[] arregloN = textoN.toCharArray();
                                    char[] arregloA = textoA.toCharArray();
                                    // Obteniendo la primera posicion de los arreglos
                                    char inicialN = arregloN[0];
                                    char inicialA = arregloA[0];
                                    String N = Character.toString(inicialN);
                                    String A = Character.toString(inicialA);
                                    String iniciales = N + A;
                                    // Generando los 3 numeros aleatorioss
                                    Random random = new Random();
                                    int numero1 = random.nextInt(10);
                                    int numero2 = random.nextInt(10);
                                    int numero3 = random.nextInt(10);
                                    // Convirtiendo los numeros a string
                                    String n1 = Integer.toString(numero1);
                                    String n2 = Integer.toString(numero2);
                                    String n3 = Integer.toString(numero3);
                                    String numeros = n1 + n2 + n3;
                                    // Generacion del PIN personal:
                                    String pin = iniciales + numeros;

                                    //Generacion del PIN...............................................................................

                                    //Enviando la informacion a Home.....................................................................
                                    Intent k = new Intent(this, Home.class); //se pasa a la pag de informacion
                                    k.putExtra("nombreEnviado",txtNombre.getText().toString()); //Se envia el nombre
                                    k.putExtra("cedulaEnviada",txtCedula.getText().toString()); //Se envia la cedula
                                    k.putExtra("apellidoEnviado",txtApellido.getText().toString()); //Se envia el apellido
                                    k.putExtra("clave1Enviada",txtClave1.getText().toString()); //Se envia la contraseña
                                    k.putExtra("preguntaEnviada",item.toString()); //Se envia la pregunta
                                    k.putExtra("respuestaEnviada",txtRespuesta.getText().toString()); //Se envia la respuesta
                                    k.putExtra("pinEnviado",pin.toString()); //Se envia la el pin
                                    k.putExtra("BalizaEnvio",enviarDatosBT);
                                    startActivity(k);
                                    //Enviando la informacion a Home.....................................................................

                                } else {
                                    // El EditText está vacío
                                    Toast.makeText(Registro.this, "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //Toast.makeText(MainActivity.this, "clave 1: "+clave1+" y clave2: "+clave2, Toast.LENGTH_SHORT).show();
                                Toast.makeText(Registro.this, "LAS CONTRASEÑAS INGRESADAS NO COINCIDEN", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        // El EditText está vacío
                        Toast.makeText(Registro.this, "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registro.this, "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                }
            } else {
                // El EditText está vacío
                Toast.makeText(Registro.this, "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El EditText está vacío
            Toast.makeText(Registro.this, "TODOS LOS CAMPOS SON OBLIGATORIOS", Toast.LENGTH_SHORT).show();
        }
    }
}
