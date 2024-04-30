package com.example.isp_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Librerias para manejar el BT:
import android.os.Handler;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import com.example.isp_app.adaptadores.ListaContactosAdapter;
import com.example.isp_app.db.DbContactos;
import com.example.isp_app.entidades.Contactos;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_CONNECT_PERMISSION = 3;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 2;
    private BluetoothAdapter mBtAdapter;
    private BluetoothSocket btSocket;
    private BluetoothDevice DispositivoSeleccionado;
    private BluetoothDevice DispositivoSeleccionado2;
    private ConnectedThread MyConexionBT;

    String Pin, Pregunta, Respuesta, Clave1, Cedula, Apellido, Nombre, Clave, enviarDatosBT, pinAUbicar;
    Button btnPerfil, btnAgregar;
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //LLAMANDO LOS PERMISOS BT:
        requestBluetoothConnectPermission();
        requestLocationPermission();

        btnPerfil = findViewById(R.id.btnPerfil);
        btnAgregar = findViewById(R.id.btnAgregar);
        listaContactos = findViewById(R.id.listaContactos);

        listaContactos = findViewById(R.id.listaContactos);

        listaContactos.setLayoutManager(new LinearLayoutManager(this));
        DbContactos dbContactos = new DbContactos(Home.this);

        listaArrayContactos = new ArrayList<>();

        ListaContactosAdapter adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);


        //crearBaseDeDatos(); //NO SE ESTA EJECUTANDO, cualquier error quitale el comentario

        Recibiendo ();

       /* if (pinAUbicar != null){
           // MyConexionBT.write(pinAUbicar);
        }*/


        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //Enviando a perfil
                Intent intent = new Intent(Home.this, Perfil.class);
                if (Nombre == null){
                    intent.putExtra("nombreEnviado"," ");
                } else {
                    intent.putExtra("nombreEnviado",Nombre.toString());
                }
                if (Apellido == null){
                    intent.putExtra("apellidoEnviado"," ");
                } else {
                    intent.putExtra("apellidoEnviado",Apellido.toString());
                }
                if (Cedula == null){
                    intent.putExtra("cedulaEnviada", "");
                } else {
                    intent.putExtra("cedulaEnviada",Cedula.toString());
                }
                if (Clave1 == null){
                    intent.putExtra("clave1Enviada", "");
                } else {
                    intent.putExtra("clave1Enviada",Clave1.toString());
                }
                if (Respuesta == null){
                    intent.putExtra("respuestaEnviada", "");
                } else {
                    intent.putExtra("respuestaEnviada",Respuesta.toString());
                }
                if (Pregunta == null){
                    intent.putExtra("preguntaEnviada","");
                } else {
                    intent.putExtra("preguntaEnviada",Pregunta.toString());
                }
                if (Pin == null){
                    intent.putExtra("pinEnviado","");
                } else {
                    intent.putExtra("pinEnviado",Pin.toString());
                }
                startActivity(intent);
            }
        });

        ConectarDispBT();

    }

    public void Recibiendo () {
        //Recbiendo informacion de Registro:
        Nombre = getIntent().getExtras().getString("nombreEnviado");
        Apellido = getIntent().getExtras().getString("apellidoEnviado");
        Cedula = getIntent().getExtras().getString("cedulaEnviada");
        Clave1 = getIntent().getExtras().getString("clave1Enviada");
        Respuesta = getIntent().getExtras().getString("respuestaEnviada");
        Pregunta = getIntent().getExtras().getString("preguntaEnviada");
        Pin = getIntent().getExtras().getString("pinEnviado");
        enviarDatosBT = getIntent().getExtras().getString("BalizaEnvio");

        //Recibiendo de InicioSesion:
        Clave = getIntent().getExtras().getString("clave1Enviada");

        Ubicar();

    }

    public void Ubicar (){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pinAUbicar = extras.getString("pinAUbicar"); //Recibiendo contacto para ubicarlo
            if (pinAUbicar == null){
                //MyConexionBT.write(" ");
            } else {
                //MyConexionBT.write(pinAUbicar);
                showToast("Pin a uicar: "+pinAUbicar);
            }

        }
    }


    //CODIGO PARA MANEJAR EL BT:
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Home.REQUEST_ENABLE_BT) {
                        Log.d(TAG, "ACTIVIDAD REGISTRADA");
                        //Toast.makeText(getBaseContext(), "ACTIVIDAD REGISTRADA", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    public void DispositivosVinculados() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            showToast("Bluetooth no disponible en este dispositivo.");
            finish();
            return;
        }
        if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            someActivityResultLauncher.launch(enableBtIntent);
        }
        //Selecciono el dispositivo bluetooth que tiene el nombre "Esp32test1"
        DispositivoSeleccionado = getBluetoothDeviceByName("ESP32test1");
        DispositivoSeleccionado2 = getBluetoothDeviceByName("ESP32test2");
    }


    //Permisos....................................................................................................................................
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_PERMISSION);
    }
    // Agrega este método para solicitar el permiso
    private void requestBluetoothConnectPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT_PERMISSION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permiso concedido, ahora puedes utilizar funciones de Bluetooth que requieran BLUETOOTH_CONNECT");
            } else {
                Log.d(TAG, "Permiso denegado, debes manejar este caso según tus necesidades");
            }
        }
    }
    //Permisos....................................................................................................................................


    //Metodo para buscar a los dispositivos bluetooth emparejados por el nombre:
    private BluetoothDevice getBluetoothDeviceByName(String name) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, " ----->>>>> ActivityCompat.checkSelfPermission");
        }
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    private void ConectarDispBT() {
        DispositivosVinculados();
        if (DispositivoSeleccionado == null) {
            showToast("Vincule los dispositvos bluetooth con los nombres ESP32test1 o ESP32test2"); //cuando lo quieras hacer con varios se modifica este if
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            btSocket = DispositivoSeleccionado.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
            btSocket.connect();
            MyConexionBT = new ConnectedThread(btSocket);
            MyConexionBT.start();

                if (Nombre == null){
                    MyConexionBT.write(" ");
                } else {
                    MyConexionBT.write(Nombre+", ");
                }
                if (Apellido == null){
                    MyConexionBT.write(" ");
                } else {
                    MyConexionBT.write(Apellido+", ");
                }
                MyConexionBT.write(Cedula);
                MyConexionBT.write(", ");
                if (Pin == null){
                    MyConexionBT.write(" ");
                } else {
                    MyConexionBT.write(Pin+", ");
                }
                MyConexionBT.write(Clave);
                MyConexionBT.write(", ");
                if (Pregunta == null){
                    MyConexionBT.write(" ");
                } else {
                    MyConexionBT.write(Pregunta+", ");
                }
                if (Respuesta == null){
                    MyConexionBT.write(" ");
                } else {
                    MyConexionBT.write(Respuesta+", ");
                }

                showToast("USTED SE ENCUENTRA EN EL MODULO #1");

        } catch (IOException e) {
            //showToast("Error al conectar con el dispositivo.");
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                btSocket = DispositivoSeleccionado2.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
                btSocket.connect();
                MyConexionBT = new ConnectedThread(btSocket);
                MyConexionBT.start();
                showToast("USTED SE ENCUENTRA EN EL MODULO #2");
            } catch (IOException a) {
                showToast("Usted no se encuentra cerca de ningun modulo cercano");
            }
        }
    }


    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer;
        ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                showToast("Error al crear el flujo de datos.");
            }
            mmOutStream = tmpOut;
            mmInStream = tmpIn;
        }
        public void write(String input) {
            try {
                byte[] bytes = input.getBytes();
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "La conexión falló", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        //Para poder leer..........................................................................................................
        public void run() {
            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    int availableBytes = mmInStream.available();
                    if (availableBytes > 0) {
                        byte[] buffer = new byte[availableBytes];
                        int numBytes = mmInStream.read(buffer);
                        // Convert the byte array to a string.
                        final String receivedData = new String(buffer, 0, numBytes);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // Actualiza el TextView de idTextIn con la data recibida.
                                //idTextIn.append(receivedData);
                                //Codigo Para Identificar la baliza de verificacion de Usuarios:.................................
                                if (receivedData.trim().equals("1")) {
                                    Toast.makeText(Home.this, "BIENVENIDO/A", Toast.LENGTH_SHORT).show();
                                }
                                // Si los datos recibidos son iguales a baliza, muestra otro Toast.
                                if (receivedData.equals("0")){
                                    Intent intent = new Intent(Home.this, MainActivity.class);
                                    Toast.makeText(Home.this, "CREDENCIALES INCORRECTAS (DESACTIVE Y VUELVA A ACTIVAR EL BLUETOOTH)", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                //Codigo Para Identificar la baliza de verificacion de Usuarios:.................................
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }
        //Para poder leer..........................................................................................................
    }


    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendCommand(String input) {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(input.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void guardarContacto(View view){
        Intent i = new Intent(Home.this, AgregarContactos.class);
        startActivity(i);
    }
}
