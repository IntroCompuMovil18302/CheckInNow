package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Eventopricipal extends AppCompatActivity {

    Button evento;
    Button filtrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventopricipal);

        evento=(Button) findViewById(R.id.button);
        filtrar=(Button) findViewById(R.id.filtrar);

        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Evento.class);
                startActivity(intent);
            }
        });

        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Filtrarevento.class);
                startActivity(intent);
            }
        });

    }
}
