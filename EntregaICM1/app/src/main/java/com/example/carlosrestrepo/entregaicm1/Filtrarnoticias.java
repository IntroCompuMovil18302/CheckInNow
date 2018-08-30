package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Filtrarnoticias extends AppCompatActivity {

    Button filnot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrarnoticias);

        filnot=(Button) findViewById(R.id.butfilnot);

        filnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Eventopricipal.class);
                startActivity(intent);
            }
        });
    }
}
