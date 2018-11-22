package com.checkinnow.checkinnow.Noticias;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.checkinnow.checkinnow.R;


public class MenuNoticiaActivity extends AppCompatActivity {


    // MenuAnfitrionFragment menufragmetn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_noticias);


        MenuNoticiaFragment menufragmetn = new MenuNoticiaFragment();
        View view = findViewById(R.id.contenedor);





        if (view == null) {
        } else {
            MenuNoticiaFragment crear1 = new MenuNoticiaFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.contenedor, crear1); //donde fragmentContainer_id es el ID del FrameLayout donde tu Fragment está contenido.
            fragmentTransaction.commit();
        }








    }

}
