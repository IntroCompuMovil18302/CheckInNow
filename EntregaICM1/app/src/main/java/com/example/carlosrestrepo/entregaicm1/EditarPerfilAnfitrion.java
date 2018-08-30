package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditarPerfilAnfitrion extends AppCompatActivity {

    private Button eliminar;
    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_anfitrion);

        eliminar = (Button) findViewById(R.id.button5);
        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                elimimar();

            }
        });

        guardar = (Button) findViewById(R.id.button8);
        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                guardar();

            }
        });
    }

    public void elimimar(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void guardar(){
        Intent intent = new Intent(this, Anfitrion.class);
        startActivity(intent);
    }
}
