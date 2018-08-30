package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearUsuarioActivity extends AppCompatActivity {

    private Button Siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        Siguiente = (Button) findViewById(R.id.Siguiente) ;
        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SiguienteCrearUsuario();

            }
        });
    }

    public void SiguienteCrearUsuario(){
        Intent Crear = new Intent(this, CrearUsuario2Activity.class);
        startActivity(Crear);
    }
}
