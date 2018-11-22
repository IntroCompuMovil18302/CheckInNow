package com.checkinnow.checkinnow.Eventos;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.checkinnow.checkinnow.R;

public class MenuEventoFragment extends Fragment {

    private Button botonagregaractividad;
    private Button botonverlugares;

    public MenuEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu_eventos, container, false);
        botonagregaractividad = (Button) v.findViewById(R.id.Agregarbutton);
        botonverlugares = (Button) v.findViewById(R.id.verbutton);
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

    private void verLugares() {
        VerEventosFragment verlugares = new VerEventosFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedorEventos, verlugares);
        transaction.addToBackStack("agregarFragverlugar");
        transaction.commit();
    }

    private void agregarLugar() {
        AgregarEventoFragment agregarlugar = new AgregarEventoFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedorEventos, agregarlugar);
        transaction.addToBackStack("agregarFragagregarlugar");
        transaction.commit();
    }


}
