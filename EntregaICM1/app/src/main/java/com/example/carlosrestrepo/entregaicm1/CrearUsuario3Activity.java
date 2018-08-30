package com.example.carlosrestrepo.entregaicm1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CrearUsuario3Activity extends AppCompatActivity {

    private Button Verificar;
    private Button Atras;
    private Button Activar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario3);
        Verificar = (Button) findViewById(R.id.VerificarTarjeta) ;
        Verificar.setOnClickListener(new View.OnClickListener() {

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

        Activar = (Button) findViewById(R.id.ActivarCuenta) ;
        Activar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ActivarCuenta();

            }
        });
    }

    public void SiguienteCrearUsuario(){
        Toast.makeText(this,
                "TARJETA VERIFICADA", Toast.LENGTH_LONG).show();
    }
    public void AtrasCrearUsuario(){
        Intent Crear = new Intent(this, CrearUsuario2Activity.class);
        startActivity(Crear);
    }

    public void ActivarCuenta(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("ACTIVACION REALIZADA");
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
}
