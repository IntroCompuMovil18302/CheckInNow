package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ComprobarCalendario extends AppCompatActivity {

    private ImageButton volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobar_calendario);

        volver = (ImageButton) findViewById(R.id.imageButton);
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                volver();

            }
        });
    }

    public void volver(){
        Intent intent = new Intent(this, VerLugar.class);
        startActivity(intent);
    }
}
