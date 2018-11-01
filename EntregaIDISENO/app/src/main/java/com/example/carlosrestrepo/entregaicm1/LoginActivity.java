package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carlosrestrepo.entregaicm1.CrearUsuario.CrearUsuarioActivity;

public class LoginActivity extends AppCompatActivity {

    private Button CrearUsuario;
    private Button IniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CrearUsuario = (Button) findViewById(R.id.CrearUsuario) ;
        CrearUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                crearUsuario();

            }
        });

        IniciarSesion = (Button) findViewById(R.id.IniciarSesion) ;
        IniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                IniciarSesion();

            }
        });

    }

    public void crearUsuario(){
        Intent Crear = new Intent(this, CrearUsuarioActivity.class);
        startActivity(Crear);
    }

    public void IniciarSesion(){
        Intent Crear = new Intent(this, SesionActivity.class);
        startActivity(Crear);
    }

    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
