package com.example.carlosrestrepo.entregaicm1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class Anfitrion extends AppCompatActivity {

    private Button editar;
    private Button agregar;
    private Button logout;
    private ListView lugares;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anfitrion);

        editar = (Button) findViewById(R.id.button3);

        agregar = (Button) findViewById(R.id.button4);

        logout = (Button) findViewById(R.id.button2);

        lugares = (ListView) findViewById(R.id._dynamic);
    }

    public void editar(){
        Intent intent = new Intent(this, EditarPerfilAnfitrion.class);
        startActivity(intent);
    }

    public void agregar(){
        Intent intent = new Intent(this, AgregarAlojamiento.class);
        startActivity(intent);
    }

    public void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void lugares(){
        Intent intent = new Intent(this, VerLugar.class);
        startActivity(intent);
    }
}
