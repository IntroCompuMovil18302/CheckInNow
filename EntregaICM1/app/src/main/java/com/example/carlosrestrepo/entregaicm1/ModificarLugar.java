package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ModificarLugar extends AppCompatActivity {

    private Button eliminar;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_lugar);

        eliminar = (Button) findViewById(R.id.button6);
        guardar = (Button) findViewById(R.id.button5);
    }

    public void eliminar(){
        Intent intent = new Intent(this, Anfitrion.class);
        startActivity(intent);
    }

    public void guardar(){
        Intent intent = new Intent(this, Anfitrion.class);
        startActivity(intent);
    }
}
