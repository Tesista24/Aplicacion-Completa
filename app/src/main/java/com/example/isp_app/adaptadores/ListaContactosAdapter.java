package com.example.isp_app.adaptadores;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isp_app.Home;
import com.example.isp_app.R;
import com.example.isp_app.entidades.Contactos;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoviewHolder> {
    ArrayList<Contactos> listaContactos;
    public ListaContactosAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos = listaContactos;
    }


    @NonNull
    @Override
    public ContactoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto,null,false);
        return new ContactoviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactoviewHolder holder, int position) {
        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewApellido.setText(listaContactos.get(position).getApellido());
        holder.viewPin.setText(listaContactos.get(position).getPin());

        String ubicacion = listaContactos.get(position).getUbicacion();
        String nombre = listaContactos.get(position).getNombre();
        String pin = listaContactos.get(position).getPin();

        //listener del botón btnUbicar
        holder.btnUbicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Notificacion Emergente:
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("CONTACTO UBICADO");

                final TextView textView = new TextView(v.getContext());
                textView.setText("Quieres seguir utilizando la Aplicacion?");
                builder.setView(textView);

                //Botones:
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(v.getContext(), "SEGUIR CONECTADO", Toast.LENGTH_SHORT).show();// Acciones al presionar el botón "Aceptar"
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(v.getContext(), "DESCONECTAME", Toast.LENGTH_SHORT).show();// Acciones al presionar el botón "Cancelar"
                        //Codigo de desconecar
                    }
                });
                //Crear y mostrar el AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                Intent k = new Intent(v.getContext(), Home.class);
                k.putExtra("pinAUbicar","u"+pin); //Se envia el pin
                v.getContext().startActivity(k);

                holder.viewUbicacion.setText(nombre); //actualmente le pone el nombre de la persona en la ubicacion
            }
        });
    }


    @Override
    public int getItemCount() {
        return listaContactos.size();
    }


    public class ContactoviewHolder extends RecyclerView.ViewHolder {
        TextView viewNombre, viewApellido, viewPin, viewUbicacion;
        Button btnUbicar; // Agrega esta línea


        public ContactoviewHolder(@NonNull View itemView) {
            super(itemView);


            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewApellido = itemView.findViewById(R.id.viewApellido);
            viewPin = itemView.findViewById(R.id.viewPin);
            viewUbicacion = itemView.findViewById(R.id.viewUbicacion);
            btnUbicar = itemView.findViewById(R.id.btnUbicar); // Agrega esta línea
        }
    }
}
