package com.checkinnow.checkinnow.Anfitrion;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.checkinnow.checkinnow.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Modelo.ContantesClass;
import Modelo.LugarClass;

import static Modelo.ContantesClass.PATHANFITRIONSTORAGEfin;
import static Modelo.ContantesClass.PATHANFITRIONSTORAGEinicio;
import static Modelo.ContantesClass.PATHLUGARESANFITRIONfin;
import static Modelo.ContantesClass.PATHLUGARESANFITRIONinicio;
import static Modelo.ContantesClass.REQUEST_IMAGE_CAPTURE;
import static Modelo.ContantesClass.TAG;
import static Modelo.ContantesClass.Uid;
import static android.app.Activity.RESULT_OK;


public class AgregarLugarFragment extends Fragment {

    private static LugarClass lugar;
    String mCurrentPhotoPath;
    BitmapFactory.Options options;
    private Button ubicacion;
    private Button gale;
    private Button menoshab;
    private Button menosban;
    private Button menoscam;
    private Button mashab;
    private Button masban;
    private Button mascam;

    private EditText nombre;
    private Spinner tipo;
    private Spinner moneda;
    private EditText habitaciones;
    private EditText camas;
    private EditText banos;
    private Switch estaciona;
    private Switch mascota;


    private EditText valor;
    private Button agregar;
    private Button foto;
    private ImageView image1;
    private ImageView image2;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private View v;
    private TextView texto;
    private Bundle result;
    private double lati;
    private double longi;
    private List<Uri> uris;
    private String key;

    public AgregarLugarFragment() {
        // Required empty public constructor
        uris = new ArrayList<Uri>();
        lugar = new LugarClass();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference(PATHANFITRIONSTORAGEinicio + Uid + PATHANFITRIONSTORAGEfin);
        this.key = myRef.push().getKey();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_agregar_lugar, container, false);

        //tipo = v.findViewById(R.id.editTextTipo);

        tipo = v.findViewById(R.id.spinnertipo);
        moneda = v.findViewById(R.id.spinnermoneda);
        habitaciones = v.findViewById(R.id.editTexthab);
        camas = v.findViewById(R.id.editTextcamas);
        banos = v.findViewById(R.id.editTextbanos);
        estaciona = v.findViewById(R.id.estacionamiento);
        mascota = v.findViewById(R.id.mascotas);

        menoshab = v.findViewById(R.id.menoshab);
        menosban = v.findViewById(R.id.menosban);
        menoscam = v.findViewById(R.id.menoscam);
        mashab = v.findViewById(R.id.mashab);
        masban = v.findViewById(R.id.masban);
        mascam = v.findViewById(R.id.mascam);

        nombre = v.findViewById(R.id.editTextnombre);
        valor = v.findViewById(R.id.editTextValor);
        ubicacion = v.findViewById(R.id.buttonUbicion);
        gale = v.findViewById(R.id.buttonGal);
        foto = v.findViewById(R.id.buttonCam);
        agregar = v.findViewById(R.id.buttonAgregar);
        image1 = v.findViewById(R.id.imagegal);
        image2 = v.findViewById(R.id.imagecam);
        requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                "Se necesita acceder al almacenamiento", ContantesClass.READ_EXTERNAL_STORAGE2);

        requestPermission(getActivity(), Manifest.permission.CAMERA,
                "Se necesita acceder a la camara", REQUEST_IMAGE_CAPTURE);

        menosban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disminuir(banos);
            }
        });

        menoshab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disminuir(habitaciones);
            }
        });

        menoscam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disminuir(camas);
            }
        });

        masban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aumentar(banos);
            }
        });

        mashab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aumentar(habitaciones);
            }
        });

        mascam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aumentar(camas);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLugar();
            }
        });

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarMapa();
            }
        });

        gale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarImagen();
            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


        return v;
    }

    private void disminuir(EditText text) {

        if (Integer.parseInt(text.getText().toString()) - 1 >= 1) {
            text.setText(String.valueOf(Integer.parseInt(text.getText().toString()) - 1));
        } else {
            Toast.makeText(v.getContext(), "Debe ser mayor a 1", Toast.LENGTH_SHORT).show();
        }

    }

    private void aumentar(EditText text) {
        text.setText(String.valueOf(Integer.parseInt(text.getText().toString()) + 1));
    }
    ////////////////////////////////////////LANZAR MAPA/////////////////////////////////////////////////////////////////

    private void lanzarMapa() {

        MapaSeleccionFragment mapagregarlugar = new MapaSeleccionFragment();

        mapagregarlugar.setTargetFragment(AgregarLugarFragment.this, ContantesClass.REQUEST_LOCATION2);


        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frameDinamico, mapagregarlugar).addToBackStack("agregarFragMapa").commit();


    }
    ////////////////////////////////////////LANZAR MAPA-/////////////////////////////////////////////////////////////////

    ////////////////////////////////////////GALERIA/////////////////////////////////////////////////////////////////
    private void agregarImagen() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, ContantesClass.IMAGE_PICKER_REQUEST2);
        } else {
            Toast.makeText(v.getContext(),
                    "Sin acceso a almacenamietno",
                    Toast.LENGTH_LONG).show();
            requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Se necesita acceder al almacenamiento", ContantesClass.READ_EXTERNAL_STORAGE2);
        }


    }

    /////////////////////////////////////////GALERIA-////////////////////////////////////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ContantesClass.IMAGE_PICKER_REQUEST2: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        //agregarStorage(imageUri);
                        uris.add(imageUri);

                        final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        image1.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            case ContantesClass.REQUEST_LOCATION2: {
                if (resultCode == Activity.RESULT_OK) {
                    /* this.result =*/

                    Bundle result2 = data.getBundleExtra("bundle");
                    lati = result2.getDouble("lat");
                    longi = result2.getDouble("long");
                    result2.clear();
                    // getFragmentManager().popBackStack();
                }
                return;
            }
            case ContantesClass.READ_EXTERNAL_STORAGE2: {   //PERMISO
                if (resultCode == RESULT_OK) {
                    Toast.makeText(v.getContext(),
                            "Comencemos",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(),
                            "Sin acceso a localización, hardware deshabilitado!",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
            case REQUEST_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {


                    File imgFile = new File(mCurrentPhotoPath);
                    //agregarStorage(Uri.fromFile(imgFile));

                    //lugar.agregarlugar(Uri.fromFile(imgFile));
                    uris.add(Uri.fromFile(imgFile));

                    if (imgFile.exists()) {
                        Log.i(TAG, "-->" + imgFile.getPath());
                        options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getPath(), options);
                        image2.setImageBitmap(myBitmap);
                    }


                }
                return;
            }
        }
    }


    /////////////////////////////////////////PERMISOS////////////////////////////////////////////////////////////////
    private void requestPermission(Activity context, String permission, String explanation, int requestId) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }
    }

    /////////////////////////////////////////PERMISOS-////////////////////////////////////////////////////////////////
    /////////////////////////////////////////CAMARA////////////////////////////////////////////////////////////////
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                Log.i(TAG, mCurrentPhotoPath);
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Log.i(TAG, mCurrentPhotoPath);
                    Uri photoURI = FileProvider.getUriForFile(v.getContext(),
                            "com.checkinnow.anfitrion",
                            photoFile);
                    Log.i(TAG, mCurrentPhotoPath);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    Log.i(TAG, mCurrentPhotoPath);
                    //galleryAddPic();
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else {
            Toast.makeText(v.getContext(),
                    "Sin acceso a camara",
                    Toast.LENGTH_LONG).show();
            requestPermission(getActivity(), Manifest.permission.CAMERA,
                    "Se necesita acceder a la camara", REQUEST_IMAGE_CAPTURE);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }
    /////////////////////////////////////////CAMARA--////////////////////////////////////////////////////////////////
    /////////////////////////////////////////STORAGE////////////////////////////////////////////////////////////////

    private void agregarStorage(Uri uri) {
        StorageReference mRef = mStorageRef.child(PATHANFITRIONSTORAGEinicio + Uid + PATHANFITRIONSTORAGEfin).child(key).child(uri.getLastPathSegment());

        mRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(v.getContext(), "SUCCESS UPLOAD", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, taskSnapshot.getUploadSessionUri().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(v.getContext(), "ERROR UPLOAD", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /////////////////////////////////////////STORAGE--////////////////////////////////////////////////////////////////

    private void agregarLugar() {

        if (validarDatos()) {
            if (lati != 0 && longi != 0) {
                if (this.uris.size() >= 4) {

                    //LugarClass lugar = new LugarClass();
                    lugar.setNombre(nombre.getText().toString());
                    lugar.setTipo(tipo.getSelectedItem().toString());
                    lugar.setMoneda(moneda.getSelectedItem().toString());
                    //lugar.setTipo(tipo.getText().toString());
                    lugar.setValor(Double.valueOf(valor.getText().toString()));
                    lugar.setPath(PATHANFITRIONSTORAGEinicio + Uid + PATHANFITRIONSTORAGEfin + key);
                    Log.i(TAG, lugar.toString());
                    lugar.setLatitude(this.lati);
                    lugar.setLongitud(this.longi);
                    lugar.setID(this.key);
                    lugar.setHabitaciones(Integer.parseInt(habitaciones.getText().toString()));
                    lugar.setCamas(Integer.parseInt(camas.getText().toString()));
                    lugar.setBanos(Integer.parseInt(banos.getText().toString()));
                    lugar.setEstacionamiento(estaciona.isChecked());
                    lugar.setMascota(mascota.isChecked());

                    for (Uri uri : this.uris) {
                        lugar.getNombreimagenes().add(uri.getLastPathSegment());
                    }

                    Log.i(TAG, "FINAL" + lugar.toString());


                    myRef = database.getReference(PATHLUGARESANFITRIONinicio + Uid + PATHLUGARESANFITRIONfin + key);
                    myRef.setValue(lugar);
                    for (Uri uri : this.uris) {
                        agregarStorage(uri);
                    }
                    getFragmentManager().popBackStack();

                } else {
                    Toast.makeText(getActivity(), "NO ingreso 4 imagenes.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "NO a seleccionado una ubicacion", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean validarDatos() {

        boolean valid = true;
        String name = nombre.getText().toString();

        if (TextUtils.isEmpty(name)) {
            nombre.setError("Requerido");
            valid = false;
        } else {
            nombre.setError(null);
        }

        try {
            Double val = Double.valueOf(valor.getText().toString());
            valor.setError(null);

            if (val == 0) {
                valor.setError("0 no es un valor");
                valid = false;
            } else {
                valor.setError(null);
            }

        } catch (Exception e) {
            valor.setError("Requerido");
            valid = false;
        }
        return valid;
    }
}
