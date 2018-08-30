package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearUsuario2Activity extends AppCompatActivity {

    private Button Siguiente;
    private Button Atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario2);
        Siguiente = (Button) findViewById(R.id.Siguiente) ;
        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SiguienteCrearUsuario();

            }
        });

        Atras = (Button) findViewById(R.id.Atras) ;
        Atras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AtrasCrearUsuario();

            }
        });
    }

    public void SiguienteCrearUsuario(){
        Intent Crear = new Intent(this, CrearUsuario3Activity.class);
        startActivity(Crear);
    }
    public void AtrasCrearUsuario(){
        Intent Crear = new Intent(this, CrearUsuarioActivity.class);
        startActivity(Crear);
    }
}
