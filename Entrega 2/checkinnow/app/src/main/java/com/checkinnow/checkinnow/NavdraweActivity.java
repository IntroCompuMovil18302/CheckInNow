package com.checkinnow.checkinnow;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.checkinnow.checkinnow.Anfitrion.AgregarLugarFragment;
import com.checkinnow.checkinnow.Anfitrion.MenuAnfitrionFragment;
import com.checkinnow.checkinnow.Anfitrion.VerlugaresFragment;
import com.checkinnow.checkinnow.Huesped.ExtraFragment;
import com.checkinnow.checkinnow.Huesped.VesreservasFragment;
import com.checkinnow.checkinnow.Huesped.consultarFragment;
import com.checkinnow.checkinnow.Huesped.mapconsulFragment;
import com.checkinnow.checkinnow.SesionIniciada.Gmap2Fragment;
import com.checkinnow.checkinnow.SesionIniciada.GmapFragment;
import com.checkinnow.checkinnow.SesionIniciada.ImportFragment;


public class NavdraweActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView latlong,textViewemail;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme6);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        textViewemail = (TextView) findViewById(R.id.textViewemail);
        email = getIntent().getStringExtra("email");

//        textViewemail.setText(email);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navdrawe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        Fragment fragment2 = null;
        android.app.FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_Huesped) {
            // Handle the camera action
            //fm.beginTransaction().replace(R.id.frameDinamico, new ImportFragment()).commit();
            android.app.FragmentManager fmm = getFragmentManager();
            fmm.beginTransaction().replace(R.id.frameDinamico, new ExtraFragment()).addToBackStack("filtrar").commit();



        }

        if (id == R.id.nav_filtroTextual) {
            // Handle the camera action
            //fm.beginTransaction().replace(R.id.frameDinamico, new ImportFragment()).commit();
            android.app.FragmentManager fmm = getFragmentManager();
            fmm.beginTransaction().replace(R.id.frameDinamico, new consultarFragment()).addToBackStack("filtrar").commit();



        } else if(id == R.id.nav_filtroposicional){

            android.app.FragmentManager frm = getFragmentManager();
            frm.beginTransaction().replace(R.id.frameDinamico, new mapconsulFragment()).addToBackStack("agregarFragverconsulmapa").commit();
        }else if(id == R.id.nav_mostrarRuta){

            android.app.FragmentManager fwm = getFragmentManager();
            fwm.beginTransaction().replace(R.id.frameDinamico, new VesreservasFragment()).addToBackStack("verreservas").commit();

        }


        else if (id == R.id.nav_gallery) {
            fm.beginTransaction().replace(R.id.frameDinamico, new GmapFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            fm.beginTransaction().replace(R.id.frameDinamico, new Gmap2Fragment()).commit();

        } else if (id == R.id.nav_agregarLugar) {
            android.app.FragmentManager frm = getFragmentManager();
            frm.beginTransaction().replace(R.id.frameDinamico, new AgregarLugarFragment()).addToBackStack("agregarFragagregarlugar").commit();

            //fm.beginTransaction().replace(R.id.frameDinamico, new MenuAnfitrionFragment()).commit();

        } else if (id == R.id.nav_verLugar) {
            android.app.FragmentManager fxm = getFragmentManager();
            fxm.beginTransaction().replace(R.id.frameDinamico, new VerlugaresFragment()).addToBackStack("agregarFragverlugar").commit();

        }

        if(fragment != null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frameDinamico,fragment);
            ft.commit();

        }

        if(fragment2 != null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frameDinamico,fragment2);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
