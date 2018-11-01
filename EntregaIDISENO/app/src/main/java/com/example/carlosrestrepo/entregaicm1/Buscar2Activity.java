package com.example.carlosrestrepo.entregaicm1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Buscar2Activity extends AppCompatActivity {

    private RelativeLayout BotonHome;
    private RelativeLayout BotonBuscar;
    private RelativeLayout BotonReservas;
    private RelativeLayout BotonCalificacion;
    private RelativeLayout BotonPerfil;
    private Button BotonReservarLugar;
    private Button botonLLegarLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar2);
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

        BotonReservarLugar = (Button) findViewById(R.id.BotonReservarLugar);
        BotonReservarLugar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonReservarLugar();

            }
        });

        botonLLegarLugares = (Button) findViewById(R.id.botonLLegarLugares);
        botonLLegarLugares.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                botonLLegarLugares();

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


    public void BotonReservarLugar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Anuncio de Reserva");
        dialogo.setMessage("Hemos realizado la Reserva solicitada, puede consultar mas datos en su correo, o en la aplicacion en la seccion de Reservas");
        dialogo.create();

        dialogo.show();
    }

    public void botonLLegarLugares(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Seguro que quiere ejecutar la aplicacion para llegar al lugar");
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
