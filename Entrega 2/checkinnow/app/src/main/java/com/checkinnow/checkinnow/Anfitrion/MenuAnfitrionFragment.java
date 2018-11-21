package com.checkinnow.checkinnow.Anfitrion;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.checkinnow.checkinnow.Huesped.mapconsulFragment;
import com.checkinnow.checkinnow.R;
import com.checkinnow.checkinnow.SesionIniciada.Gmap2Fragment;

public class MenuAnfitrionFragment extends Fragment {

    private Button botonagregaractividad;
    private Button botonverlugares;
    private Button punto1;
    private Button punto2;


    public MenuAnfitrionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu_anfitrion, container, false);
        botonagregaractividad = (Button) v.findViewById(R.id.Agregarbutton);
        botonverlugares = (Button) v.findViewById(R.id.verbutton);
        punto1 = (Button) v.findViewById(R.id.punto1);
        punto2 = (Button) v.findViewById(R.id.punto2);

        punto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultamapa();
            }
        });

        botonagregaractividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               agregarLugar();
            }
        });

        botonverlugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLugares();
            }
        });

        return v;
    }

    private void consultamapa() {

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frameDinamico, new mapconsulFragment()).addToBackStack("agregarFragverlugar").commit();

    }

    private void verLugares() {

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frameDinamico, new VerlugaresFragment()).addToBackStack("agregarFragverlugar").commit();
    }

    private void agregarLugar() {

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frameDinamico, new AgregarLugarFragment()).addToBackStack("agregarFragagregarlugar").commit();

    }



}
