package com.example.carlosrestrepo.entregaicm1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class BuscarActivity extends AppCompatActivity {

    private RelativeLayout BotonHome;
    private RelativeLayout BotonPerfil;
    private RelativeLayout BotonBuscar;
    private RelativeLayout BotonReservas;
    private RelativeLayout BotonCalificacion;
    private Button botonBuscarLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
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

        botonBuscarLugares = (Button) findViewById(R.id.botonBuscarLugares);
        botonBuscarLugares.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                botonBuscarLugares();

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

    public void BotonModificar(){
        Intent Crear = new Intent(this, Perfil2Activity.class);
        startActivity(Crear);
    }


    public void botonBuscarLugares(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("?Seguro que quiere realizar la busqueda con estos datos?");
        dialogo.setMessage("Continuar");
        dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Inicio();

            }
        });

        dialogo.create();

        dialogo.show();
    }

    public void Inicio(){
        Intent Crear = new Intent(this, Buscar2Activity.class);
        startActivity(Crear);
    }

    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
