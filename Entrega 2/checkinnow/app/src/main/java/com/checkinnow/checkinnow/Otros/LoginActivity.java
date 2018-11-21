package com.checkinnow.checkinnow.Otros;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.checkinnow.checkinnow.CrearUsuario.CrearUsuarioActivity;
import com.checkinnow.checkinnow.NavdraweActivity;
import com.checkinnow.checkinnow.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Modelo.ContantesClass;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText CorreoIn, ContrasenaIn;
    private Button CrearUsuario;
    private Button IniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme6);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        permisoAcceso();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AVISO", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AVISO", "onAuthStateChanged:signed_out");
                }
            }
        };
        CorreoIn = (EditText) findViewById(R.id.TextoUsuario);
        ContrasenaIn = (EditText) findViewById(R.id.TextoContraseña);

        validateForm();
        CrearUsuario = (Button) findViewById(R.id.CrearUsuario) ;
        CrearUsuario.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                crearUsuario();

            }
        });

        IniciarSesion = (Button) findViewById(R.id.IniciarSesion) ;
        IniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signInUser();

            }
        });

    }

    public void crearUsuario(){
        Intent Crear = new Intent(this, CrearUsuarioActivity.class);
        startActivity(Crear);
    }

    public void IniciarSesion(){
        Intent Crear = new Intent(this, SesionActivity.class);
        startActivity(Crear);
    }

    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


    private boolean validateForm() {
        boolean valid = true;
        String email = CorreoIn.getText().toString();
        if (TextUtils.isEmpty(email)) {
            CorreoIn.setError("Required.");
            valid = false;
        } else {
            CorreoIn.setError(null);
        }
        String password = ContrasenaIn.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ContrasenaIn.setError("Required.");
            valid = false;
        } else {
            ContrasenaIn.setError(null);
        }
        return valid;
    }

    protected void signInUser(){
        if(validateForm()){
            final String email = CorreoIn.getText().toString();
            final String password = ContrasenaIn.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Log.d("INICIO",
                                        "signInWithEmail:onComplete:" + task.isSuccessful());
                                FirebaseUser user = mAuth.getCurrentUser();
                                ContantesClass.Uid=user.getUid();
                                Intent Crear = new Intent(LoginActivity.this, NavdraweActivity.class);
                                Crear.putExtra("email",email);
                                startActivity(Crear);
                            } else{
                                if (!task.isSuccessful()) {
                                    Log.w("INICIO",
                                            "signInWithEmail:failed"
                                            , task.getException());
                                    Toast.makeText(LoginActivity.this, "FALLO INICIO",
                                            Toast.LENGTH_SHORT).show();
                                    CorreoIn.setText("");
                                    ContrasenaIn.setText("");
                                }
                            }
                        }
                    });
        }
    }


    @SuppressLint("NewApi")
    private void permisoAcceso(){
        if(ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,10);
        }
    }


}
