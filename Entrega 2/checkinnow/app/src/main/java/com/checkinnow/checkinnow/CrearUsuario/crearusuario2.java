package com.checkinnow.checkinnow.CrearUsuario;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.checkinnow.checkinnow.Clases.Usuario;
import com.checkinnow.checkinnow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class crearusuario2 extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public static final String PATH_USERS="users/";

    private Button Siguiente;

    String nombre, apellido, contrasena, pais, fecha,email,url,uid;

    EditText textoNumeroCelular;
    EditText textoCodigo;
    String StringtextoNumeroCelular;
    String StringtextoCodigo;
    public crearusuario2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_crearusuario2,container,false);
        Siguiente = (Button) v.findViewById(R.id.Siguiente) ;
        textoNumeroCelular = (EditText) v.findViewById(R.id.textoNumeroCelular);
        textoCodigo = (EditText) v.findViewById(R.id.textoCodigo);

        database= FirebaseDatabase.getInstance();
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

        nombre =  getArguments().getString("nombre");
        apellido =  getArguments().getString("apellido");
        contrasena =  getArguments().getString("contrasena");
        pais =  getArguments().getString("pais");
        fecha =  getArguments().getString("fecha");
        email = getArguments().getString("email");
        url = getArguments().getString("url");

        Log.d("MENSAKE", "MENSAJE R1 " + nombre);
        Log.d("MENSAKE", "MENSAJE R2  " + apellido);
        Log.d("MENSAKE", "MENSAJE R3  " + contrasena);
        Log.d("MENSAKE", "MENSAJE R4  " + pais);
        Log.d("MENSAKE", "MENSAJE R5  " + fecha);
        Log.d("MENSAKE", "MENSAJE R6  " + url);
        Log.d("MENSAKE", "MENSAJE R7  " + email);



        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                StringtextoNumeroCelular = textoNumeroCelular.getText().toString();
                StringtextoCodigo = textoCodigo.getText().toString();

                signInUser(email,contrasena);
                usuarios();

                fragmentoSiguiente(StringtextoCodigo,StringtextoNumeroCelular);

            }
        });





        return v;
    }


    public void fragmentoSiguiente(String stringtextoCodigo, String stringtextoNumeroCelular){

        if(stringtextoCodigo.isEmpty()||stringtextoNumeroCelular.isEmpty()){
            Toast.makeText(getActivity(),"Falta llenar información",Toast.LENGTH_LONG).show();
        }else {
            crearusuario3 crear3 = new crearusuario3();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dinamicoCrear, crear3);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    public void fragmentoAtras(){
        crearusuario1 crear1 = new crearusuario1();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dinamicoCrear, crear1);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void usuarios(){
        Log.d("AVISO",
                "signInWithEmail:onComplete ID: " + PATH_USERS+uid );
        Usuario myUser = new Usuario();
        myUser.setName(nombre);
        myUser.setApelligo(apellido);
        myUser.setFecha(fecha);
        myUser.setPais(pais);
        myUser.setContraseña(contrasena);
        myUser.setUtlImg(url);
        myRef=database.getReference(PATH_USERS+uid);
        Log.d("AVISO",
                "signInWithEmail:onComplete ID: " + PATH_USERS+uid );
        myRef.setValue(myUser);

    }

    protected void signInUser(String email ,String password){

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Log.d("INICIO",
                                        "signInWithEmail:onComplete:" + task.isSuccessful());
                                                           } else{
                                if (!task.isSuccessful()) {
                                    Log.w("INICIO",
                                            "signInWithEmail:failed"
                                            , task.getException());
                                    Toast.makeText(getActivity(), "FALLO INICIO",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        Log.d("AVISO",
                "SIGN USER : " + mAuth.getUid() );
        uid= mAuth.getUid();
    }


}
