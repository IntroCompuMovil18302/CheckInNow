package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgregarAlojamiento extends AppCompatActivity {

    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alojamiento);

        guardar = (Button) findViewById(R.id.button8);
        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                guardar();

            }
        });
    }

    public void guardar(){
        Intent intent = new Intent(this, Anfitrion.class);
        startActivity(intent);
    }
}
