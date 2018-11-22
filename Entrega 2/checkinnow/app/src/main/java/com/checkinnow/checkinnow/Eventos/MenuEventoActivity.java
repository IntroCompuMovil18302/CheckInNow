package com.checkinnow.checkinnow.Eventos;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.checkinnow.checkinnow.R;


public class MenuEventoActivity extends AppCompatActivity {


    // MenuAnfitrionFragment menufragmetn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_eventos);


        MenuEventoFragment menufragmetn = new MenuEventoFragment();
        View view = findViewById(R.id.contenedorEventos);

        if (view == null) {
        } else {
            MenuEventoFragment crear1 = new MenuEventoFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.contenedorEventos, crear1); //donde fragmentContainer_id es el ID del FrameLayout donde tu Fragment está contenido.
            fragmentTransaction.commit();
        }


    }

}
