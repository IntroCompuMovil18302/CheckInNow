package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerLugar extends AppCompatActivity {

    private Button modificar;
    private Button calendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lugar);

        modificar = (Button) findViewById(R.id.button5);
        modificar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                modificar();

            }
        });

        calendario = (Button) findViewById(R.id.button6);
        calendario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                calendario();

            }
        });
    }

    public void modificar(){
        Intent intent = new Intent(this, ModificarLugar.class);
        startActivity(intent);
    }

    public void calendario(){
        Intent intent = new Intent(this, ComprobarCalendario.class);
        startActivity(intent);
    }
}
