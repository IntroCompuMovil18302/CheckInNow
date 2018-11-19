package com.checkinnow.checkinnow.CrearUsuario;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.checkinnow.checkinnow.Otros.LoginActivity;
import com.checkinnow.checkinnow.R;


public class crearusuario3 extends Fragment {

    private Button Verificar;
    private Button Atras;
    private Button Activar;

    EditText NumeroTarjeta;
    EditText NumeroCCV;
    EditText FechaVencimiento;

    String stringNumeroTarjeta;
    String stringNumeroCCV;
    String stringFechaVencimiento;
    boolean Verificado = false;

    public crearusuario3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_crearusuario3,container,false);
        NumeroCCV = (EditText) v.findViewById(R.id.NumeroCCV);
        NumeroTarjeta = (EditText) v.findViewById(R.id.NumeroTarjeta);
        FechaVencimiento = (EditText) v.findViewById(R.id.FechaVencimiento);

        Atras = (Button)  v.findViewById(R.id.Atras) ;
        Atras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fragmentoAtras();

            }
        });

        Verificar = (Button) v.findViewById(R.id.VerificarTarjeta) ;
        Verificar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stringFechaVencimiento = FechaVencimiento.getText().toString();
                stringNumeroCCV = NumeroCCV.getText().toString();
                stringNumeroTarjeta = NumeroTarjeta.getText().toString();

                Verificado=SiguienteCrearUsuario(stringFechaVencimiento,stringNumeroCCV,stringNumeroTarjeta);

            }
        });


        Activar = (Button) v.findViewById(R.id.ActivarCuenta) ;
        Activar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ActivarCuenta(Verificado);

            }
        });

        return v;
    }


    public void fragmentoAtras(){
        crearusuario2 crear2 = new crearusuario2();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dinamicoCrear, crear2);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public boolean SiguienteCrearUsuario(String stringFechaVencimiento, String stringNumeroCCV, String stringNumeroTarjeta){
        if(stringFechaVencimiento.isEmpty()|| stringNumeroCCV.isEmpty() || stringNumeroTarjeta.isEmpty()){
            Toast.makeText(getActivity(),"Falta llenar información",Toast.LENGTH_LONG).show();
            return false;
        }else {
            Toast.makeText(getActivity(), "TARJETA VERIFICADA", Toast.LENGTH_LONG).show();
        return true;
        }
    }


    public void ActivarCuenta(boolean verificado){

        if(verificado==true) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());

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
        }else{
            Toast.makeText(getActivity(),"Verificar primero la tarjeta",Toast.LENGTH_LONG).show();
        }

    }

    public void Inicio(){

        Intent Crear = new Intent(getActivity(), LoginActivity.class);
        startActivity(Crear);
    }



}
