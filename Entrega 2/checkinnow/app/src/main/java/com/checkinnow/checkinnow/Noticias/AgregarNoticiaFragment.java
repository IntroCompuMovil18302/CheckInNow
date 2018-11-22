package com.checkinnow.checkinnow.Noticias;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.checkinnow.checkinnow.Noticias.ModeloNoticias.ContantesClass;
import com.checkinnow.checkinnow.Noticias.ModeloNoticias.NoticiasClass;
import com.checkinnow.checkinnow.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.checkinnow.checkinnow.Noticias.ModeloNoticias.ContantesClass.PATHANFITRIONSTORAGE;
import static com.checkinnow.checkinnow.Noticias.ModeloNoticias.ContantesClass.PATHLUGARESANFITRION;
import static com.checkinnow.checkinnow.Noticias.ModeloNoticias.ContantesClass.REQUEST_IMAGE_CAPTURE;
import static com.checkinnow.checkinnow.Noticias.ModeloNoticias.ContantesClass.TAG;


public class AgregarNoticiaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static NoticiasClass lugar;
    String mCurrentPhotoPath;
    BitmapFactory.Options options;
    private Button ubicacion;
    private Button gale;
    private EditText nombre;
    private Spinner tipo;
    private EditText descripcion;
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


    //FECHA SELECTION OPTION

    private ImageButton fechaDesde,fechaHasta;
    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    String StringTextoFecha;

    //FECHA SELECTION OPTION




    public AgregarNoticiaFragment() {
        // Required empty public constructor
        uris = new ArrayList<Uri>();
        lugar = new NoticiasClass();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference(PATHANFITRIONSTORAGE);
        this.key = myRef.push().getKey();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_agregar_noticias, container, false);

        tipo = v.findViewById(R.id.editTextTipo);
        nombre = v.findViewById(R.id.editTextnombre);
        descripcion = v.findViewById(R.id.editTextDescripcion);
        ubicacion = v.findViewById(R.id.buttonUbicion);
        gale = v.findViewById(R.id.buttonGal);
        foto = v.findViewById(R.id.buttonCam);
        agregar = v.findViewById(R.id.buttonAgregar);
        image1 = v.findViewById(R.id.imagegal);
        image2 = v.findViewById(R.id.imagecam);

        fechaDesde = v.findViewById(R.id.fechaDesde);
        fechaHasta = v.findViewById(R.id.fechaHasta);

        fechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFechaDesde();
            }
        });

        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFechaHasta();
            }
        });

        ArrayAdapter<CharSequence> adapterNoticias = ArrayAdapter.createFromResource(getActivity(),R.array.TipoNoticia,android.R.layout.simple_spinner_item);
        adapterNoticias.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        String[] letra = {"A","B","C","D","E"};
        tipo.setAdapter(adapterNoticias);


        requestPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                "Se necesita acceder al almacenamiento", ContantesClass.READ_EXTERNAL_STORAGE2);

        requestPermission(getActivity(), Manifest.permission.CAMERA,
                "Se necesita acceder a la camara", REQUEST_IMAGE_CAPTURE);

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
    ////////////////////////////////////////LANZAR MAPA/////////////////////////////////////////////////////////////////

    private void lanzarMapa() {

        MapaSeleccionFragment mapagregarlugar = new MapaSeleccionFragment();

        mapagregarlugar.setTargetFragment(AgregarNoticiaFragment.this, ContantesClass.REQUEST_LOCATION2);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameDinamico, mapagregarlugar);
        transaction.addToBackStack("agregarFragMapa");
        transaction.commit();

    }
    ////////////////////////////////////////LANZAR MAPA-/////////////////////////////////////////////////////////////////

    ////////////////////////////////////////GALERIA/////////////////////////////////////////////////////////////////
    private void agregarImagen() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, ContantesClass.IMAGE_PICKER_REQUEST2);
        } else {
            Toast.makeText(getActivity(),
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
                    Toast.makeText(getActivity(),
                            "Comencemos",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),
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
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            "com.checkinnow.checkinnow",
                            photoFile);
                    Log.i(TAG, mCurrentPhotoPath);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    Log.i(TAG, mCurrentPhotoPath);
                    //galleryAddPic();
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        } else {
            Toast.makeText(getActivity(),
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
        StorageReference mRef = mStorageRef.child(PATHANFITRIONSTORAGE).child(key).child(uri.getLastPathSegment());

        mRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       // Toast.makeText(getActivity(), "SUCCESS UPLOAD", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, taskSnapshot.getUploadSessionUri().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), "ERROR UPLOAD", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /////////////////////////////////////////STORAGE--////////////////////////////////////////////////////////////////

    private void agregarLugar() {

        if (validarDatos()) {
            if (lati != 0 && longi != 0) {
                if (this.uris.size() >= 1) {




                    //LugarClass lugar = new LugarClass();
                    lugar.setNombre(nombre.getText().toString());
                    lugar.setTipo(tipo.getSelectedItem().toString());
                    
                    lugar.setDescripcion(String.valueOf(descripcion.getText().toString()));
                    lugar.setPath(PATHANFITRIONSTORAGE + key);
                    Log.i(TAG, lugar.toString());
                    lugar.setLatitude(this.lati);
                    lugar.setLongitud(this.longi);
                    lugar.setID(this.key);

                    for (Uri uri : this.uris) {
                        lugar.getNombreimagenes().add(uri.getLastPathSegment());
                    }

                    Log.i(TAG, "FINAL" + lugar.toString());


                    myRef = database.getReference(PATHLUGARESANFITRION + key);
                    myRef.setValue(lugar);
                    for (Uri uri : this.uris) {
                        agregarStorage(uri);
                    }
                    getFragmentManager().popBackStack();

                } else {
                    Toast.makeText(getActivity(), "NO ingreso la imagen de portada del evento.", Toast.LENGTH_SHORT).show();
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

        String tipe = tipo.getSelectedItem().toString();
        if (TextUtils.isEmpty(tipe)) {
//            tipo.setError("Requerido");
            valid = false;
        } else {
//            tipo.setError(null);
        }
        try {
            String val = String.valueOf(descripcion.getText().toString());

            if (val.equals("")) {
                descripcion.setError("0 no es un valor");
                valid = false;
            } else {
                descripcion.setError(null);
            }

        } catch (Exception e) {
            descripcion.setError("Requerido");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    private void obtenerFechaDesde(){
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
                lugar.setFechaDesde(diaFormateado + BARRA + mesFormateado + BARRA + year);
//                lugar.setFechaHasta(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void obtenerFechaHasta(){
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
//                lugar.setFechaDesde(diaFormateado + BARRA + mesFormateado + BARRA + year);
                lugar.setFechaHasta(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

}
