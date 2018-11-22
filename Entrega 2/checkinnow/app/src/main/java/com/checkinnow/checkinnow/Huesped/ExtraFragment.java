package com.checkinnow.checkinnow.Huesped;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.checkinnow.checkinnow.CrearUsuario.crearusuario2;
import com.checkinnow.checkinnow.Eventos.VerEventosFragment;
import com.checkinnow.checkinnow.Noticias.VerNoticiasFragment;
import com.checkinnow.checkinnow.R;


public class ExtraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button botonNoticias,botonEventos;

    public ExtraFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_extra, container, false);


        botonEventos = v.findViewById(R.id.botonEvento);
        botonNoticias = v.findViewById(R.id.botonNoticia);

        botonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerEventosFragment crear2 = new VerEventosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameDinamico, crear2);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        botonNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerNoticiasFragment crear2 = new VerNoticiasFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameDinamico, crear2);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });


        return v;
    }


}
