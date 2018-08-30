package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Noticiaprincipal extends AppCompatActivity {

    Button noticia;
    Button filtrarnot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticiaprincipal);

        noticia=(Button)findViewById(R.id.buttonnotnot);
        filtrarnot=(Button)findViewById(R.id.filtrarnot);

        noticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Noticia.class);
                startActivity(intent);
            }
        });

        filtrarnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Filtrarnoticias.class);
                startActivity(intent);
            }
        });
    }


}
