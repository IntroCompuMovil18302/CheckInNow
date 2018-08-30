package com.example.carlosrestrepo.entregaicm1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SesionActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    private RelativeLayout BotonHome;
    private RelativeLayout BotonPerfil;
    private RelativeLayout BotonBuscar;
    private RelativeLayout BotonReservas;
    private RelativeLayout BotonCalificacion;

    Button Eventos;
    Button Noticias;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        BotonHome= (RelativeLayout) findViewById(R.id.BotonHome);
        BotonHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonHome();

            }
        });

        BotonPerfil = (RelativeLayout) findViewById(R.id.BotonPerfil) ;
        BotonPerfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonPerfil();

            }
        });

        BotonBuscar = (RelativeLayout) findViewById(R.id.BotonBuscar) ;
        BotonBuscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonBuscar();

            }
        });

        BotonReservas = (RelativeLayout) findViewById(R.id.BotonReservas) ;
        BotonReservas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonReservas();

            }
        });

        BotonCalificacion = (RelativeLayout) findViewById(R.id.BotonCalificacion) ;
        BotonCalificacion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonCalificacion();

            }
        });


        Eventos=(Button)findViewById(R.id.buteventos);
        Noticias=(Button)findViewById(R.id.butnoticias);

        Eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Eventopricipal.class);
                startActivity(intent);
            }
        });

        Noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Noticiaprincipal.class);
                startActivity(intent);
            }
        });



    }

    public void BotonHome(){
        Intent Crear = new Intent(this, SesionActivity.class);
        startActivity(Crear);
    }
    public void BotonPerfil(){
        Intent Crear = new Intent(this, PerfilActivity.class);
        startActivity(Crear);
    }
    public void BotonBuscar(){
        Intent Crear = new Intent(this, BuscarActivity.class);
        startActivity(Crear);
    }
    public void BotonReservas(){
        Intent Crear = new Intent(this, ReservasActivity.class);
        startActivity(Crear);
    }
    public void BotonCalificacion(){
        Intent Crear = new Intent(this, CalificacionActivity.class);
        startActivity(Crear);
    }


    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
