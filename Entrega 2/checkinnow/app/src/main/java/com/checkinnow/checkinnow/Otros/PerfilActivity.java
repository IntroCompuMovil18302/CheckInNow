package com.checkinnow.checkinnow.Otros;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.checkinnow.checkinnow.R;

public class PerfilActivity extends AppCompatActivity {

    private RelativeLayout BotonHome;
    private RelativeLayout BotonPerfil;
    private RelativeLayout BotonBuscar;
    private RelativeLayout BotonReservas;
    private RelativeLayout BotonCalificacion;
    private Button BotonModificar;
    private Button BotonEliminarSesion;
    private Button BotonCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
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

        BotonModificar = (Button) findViewById(R.id.BotonModificar);
        BotonModificar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BotonModificar();

            }
        });

        BotonCerrarSesion = (Button) findViewById(R.id.BotonCerrarSesion) ;
        BotonCerrarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CerrarSesion();

            }
        });

        BotonEliminarSesion = (Button) findViewById(R.id.BotonEliminarSesion) ;
        BotonEliminarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EliminarPerfil();

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

    public void CerrarSesion(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("CERRAR SESION REALIZADA");
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

    public void EliminarPerfil(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Seguro que Quiere Eliminar su Cuenta");
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
        Intent Crear = new Intent(this, LoginActivity.class);
        startActivity(Crear);
    }

    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
