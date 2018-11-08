package com.checkinnow.checkinnow.CrearUsuario;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.checkinnow.checkinnow.Otros.LoginActivity;
import com.checkinnow.checkinnow.R;


public class CrearUsuarioActivity extends AppCompatActivity {

    static boolean dinamica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme6);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);


        View view = findViewById(R.id.dinamicoCrear);

        if(view == null){
            dinamica = false;
        }
        else{
            crearusuario1 crear1 = new crearusuario1();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.dinamicoCrear, crear1); //donde fragmentContainer_id es el ID del FrameLayout donde tu Fragment está contenido.
            fragmentTransaction.commit();
        }

    }


    public void Inicio(){
        Intent Crear = new Intent(this, LoginActivity.class);
        startActivity(Crear);
    }

    public void onBackPressed() {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            dialogo.setTitle("Alerta");
            dialogo.setMessage("Si continua, no se guardarán los datos, y el proceso se cancelara.");
            dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Inicio();
                    dialog.cancel();

                }
            });

            dialogo.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            dialogo.create();

            dialogo.show();



    }






}
