package com.example.isp_app;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    Button btnUbicar;
    TextView viewUbicacion;

    public MyViewHolder(View itemView) {
        super(itemView);
        btnUbicar = itemView.findViewById(R.id.btnUbicar);
        viewUbicacion = itemView.findViewById(R.id.viewUbicacion);

        btnUbicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                // Imprime la posición en la consola
                System.out.println("Posición del adaptador: " + position);
                viewUbicacion.setText("CONTACTO UBICADO");

            }
        });
    }
}
