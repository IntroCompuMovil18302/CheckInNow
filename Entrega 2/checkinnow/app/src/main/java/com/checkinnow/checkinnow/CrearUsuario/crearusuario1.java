package com.checkinnow.checkinnow.CrearUsuario;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.checkinnow.checkinnow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;


public class crearusuario1 extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private final int PICK_IMAGE_REQUEST = 71;


    private Button Siguiente;
    private ImageButton botonParaFecha;
    EditText TextoFecha;
    CircleImageView ImagenCircular;

    EditText NombresRegistro;
    EditText ApellidosRegistros;
    EditText CorreoRegistro;
    EditText ContaseñaRegistro;
    Spinner PaisRegistro;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    String StringTextoFecha;
    String StringNombresRegistro;
    String StringApellidosRegistros;
    String StringCorreoRegistro;
    String StringContaseñaRegistro;
    String StringPaisRegistro;
    String urlImagen;
    String uid;


    public crearusuario1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_crearusuario1,container,false);
        TextoFecha = (EditText) v.findViewById(R.id.FechaNacimiento);
        NombresRegistro = (EditText) v.findViewById(R.id.NombresRegistro);
        ApellidosRegistros = (EditText) v.findViewById(R.id.ApellidosRegistros);
        CorreoRegistro = (EditText) v.findViewById(R.id.CorreoRegistro);
        ContaseñaRegistro = (EditText) v.findViewById(R.id.ContaseñaRegistro);
        PaisRegistro = (Spinner) v.findViewById(R.id.PaisEdit);
        Siguiente = (Button) v.findViewById(R.id.Siguiente);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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

        PaisRegistro.setEnabled(true);
        PaisRegistro.setFocusable(true);
        ArrayAdapter<CharSequence> adapterNoticias = ArrayAdapter.createFromResource(getActivity(),R.array.paises_array,android.R.layout.simple_spinner_item);
        adapterNoticias.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        PaisRegistro.setAdapter(adapterNoticias);

//        validateForm();
        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                StringTextoFecha=TextoFecha.getText().toString();
                StringNombresRegistro=NombresRegistro.getText().toString();
                StringApellidosRegistros=ApellidosRegistros.getText().toString();
                StringCorreoRegistro=CorreoRegistro.getText().toString();
                Log.d("AVISO", "MENSAJE A 0 CORREO  " + StringCorreoRegistro);
                StringContaseñaRegistro=ContaseñaRegistro.getText().toString();
                StringPaisRegistro=PaisRegistro.getSelectedItem().toString();

                if(StringCorreoRegistro.isEmpty() || StringContaseñaRegistro.isEmpty()) {

                }else{
                    crearUSuario(StringCorreoRegistro, StringContaseñaRegistro);
                }
                fragmentoSiguiente(StringTextoFecha,StringNombresRegistro,StringApellidosRegistros,StringCorreoRegistro,StringContaseñaRegistro,StringPaisRegistro);
            }
        });


        botonParaFecha = (ImageButton) v.findViewById(R.id.ImagenFechaNacimiento);
        botonParaFecha.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                obtenerFecha();
                uploadImage(StringCorreoRegistro);

            }
        });

        ImagenCircular = (CircleImageView) v.findViewById(R.id.imagenPerfil);
        ImagenCircular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CargarImagen();

            }
        });


        return v;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                TextoFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }



    public void CargarImagen(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());

        dialogo.setTitle("Cargar Foto para Perfil");
        dialogo.setMessage("Acceder a galeria de fotos");
        dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                chooseImage();
                dialog.cancel();

            }
        });

        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogo.create();

        dialogo.show();
    }

    public void fragmentoSiguiente(String stringTextoFecha, String stringNombresRegistro, String stringApellidosRegistros, String stringCorreoRegistro, String stringContaseñaRegistro, String stringPaisRegistro){

        Bundle args = new Bundle();
        args.putString("nombre", stringNombresRegistro);
        args.putString("apellido", stringApellidosRegistros);
        args.putString("contrasena", stringContaseñaRegistro);
        args.putString("pais", stringPaisRegistro);
        args.putString("fecha", stringTextoFecha);
        args.putString("email",stringCorreoRegistro);
        args.putString("url",urlImagen);

        Log.d("MENSAJE",
                "URL IMAGEN envio : " + urlImagen );
        Log.d("MENSAJE",
                "EMAIL  envio : " + stringCorreoRegistro );




        if(stringNombresRegistro.isEmpty() || stringTextoFecha.isEmpty() || stringApellidosRegistros.isEmpty() || stringContaseñaRegistro.isEmpty()||stringCorreoRegistro.isEmpty()||stringPaisRegistro.isEmpty()){
            Toast.makeText(getActivity(),"Hace falta llenar información", Toast.LENGTH_LONG).show();
        }else {

            crearusuario2 crear2 = new crearusuario2();
            crear2.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dinamicoCrear, crear2);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }


    private void crearUSuario(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("CREAR", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){ //Update user Info
                                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();
                                upcrb.setDisplayName(NombresRegistro.getText().toString()+" "+ApellidosRegistros.getText().toString());
                                upcrb.setPhotoUri(Uri.parse("path/to/pic"));//fake uri, real one coming soon
                                user.updateProfile(upcrb.build());
                            }
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "FALLO por "+ task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e("CREAR", task.getException().getMessage());
                        }
                    }
                });
    }




    private boolean validateForm() {
        boolean valid = true;
        String email = CorreoRegistro.getText().toString();
        if (TextUtils.isEmpty(email)) {
            CorreoRegistro.setError("Required.");
            valid = false;
        } else {
            CorreoRegistro.setError(null);
        }
        String password = ContaseñaRegistro.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ContaseñaRegistro.setError("Required.");
            valid = false;
        } else {
            ContaseñaRegistro.setError(null);
        }
        return valid;
    }

    private void uploadImage(String emailu) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            int random = ThreadLocalRandom.current().nextInt(1, 1000);
            int random2 = ThreadLocalRandom.current().nextInt(1, 1000);
            String Random = String.valueOf(random) + String.valueOf(random2) ;

            StorageReference ref = storageReference.child("images/"+ Random);
            Log.d("MENSAKE", "MENSAJE A UPLOAD CORREO  " + Random);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Subida", Toast.LENGTH_SHORT).show();

                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            urlImagen = downloadUrl.toString();
                            Log.d("AVISO", "MENSAJE A URL  " + urlImagen);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Falla "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Subiendo "+(int)progress+"%");
                        }
                    });
        }else{
            Toast.makeText(getActivity(),"NINGUN ARCHIVO ELEGIDO",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                ImagenCircular.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}