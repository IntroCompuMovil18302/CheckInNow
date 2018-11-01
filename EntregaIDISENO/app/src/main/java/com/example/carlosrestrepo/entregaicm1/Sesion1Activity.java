package com.example.carlosrestrepo.entregaicm1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Sesion1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion1);

        if(getSupportActionBar()!= null)
            getSupportActionBar().setElevation(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),new String[]{
                "Item 1", "Item 2", "Item3"
        }));


        if(tabLayout!=null){
            tabLayout.setupWithViewPager(viewPager);
        }


    }



    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
