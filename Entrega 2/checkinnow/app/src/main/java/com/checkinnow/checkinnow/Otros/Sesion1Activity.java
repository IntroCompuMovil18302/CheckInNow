package com.checkinnow.checkinnow.Otros;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.checkinnow.checkinnow.R;

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
