package com.example.carlosrestrepo.entregaicm1.CrearUsuario;

import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlosrestrepo.entregaicm1.R;


public class crearusuario2 extends Fragment {

    private Button Siguiente;

    EditText textoNumeroCelular;
    EditText textoCodigo;
    String StringtextoNumeroCelular;
    String StringtextoCodigo;
    public crearusuario2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_crearusuario2,container,false);
        Siguiente = (Button) v.findViewById(R.id.Siguiente) ;
        textoNumeroCelular = (EditText) v.findViewById(R.id.textoNumeroCelular);
        textoCodigo = (EditText) v.findViewById(R.id.textoCodigo);
        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                StringtextoNumeroCelular = textoNumeroCelular.getText().toString();
                StringtextoCodigo = textoCodigo.getText().toString();
                fragmentoSiguiente(StringtextoCodigo,StringtextoNumeroCelular);

            }
        });





        return v;
    }


    public void fragmentoSiguiente(String stringtextoCodigo, String stringtextoNumeroCelular){

        if(stringtextoCodigo.isEmpty()||stringtextoNumeroCelular.isEmpty()){
            Toast.makeText(getActivity(),"Falta llenar información",Toast.LENGTH_LONG).show();
        }else {
            crearusuario3 crear3 = new crearusuario3();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dinamicoCrear, crear3);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    public void fragmentoAtras(){
        crearusuario1 crear1 = new crearusuario1();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dinamicoCrear, crear1);
        transaction.addToBackStack(null);

        transaction.commit();
    }


}
