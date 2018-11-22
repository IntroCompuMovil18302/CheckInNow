package com.checkinnow.checkinnow.Eventos;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.checkinnow.checkinnow.Eventos.ModeloEventos.EventosAdapter;
import com.checkinnow.checkinnow.Eventos.ModeloEventos.EventosClass;
import com.checkinnow.checkinnow.R;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.checkinnow.checkinnow.Eventos.ModeloEventos.ContantesTwoClass.PATHLUGARESANFITRION;
import static com.checkinnow.checkinnow.Eventos.ModeloEventos.ContantesTwoClass.TAG;


public class VerEventosFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    List<List<String>> datos;
    List<String> temp;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private View v;
    private ListView list;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private Spinner spinnerTipoBuscar,spinnerBuscarLugarFiltro;
    private EditText textoBuscarLugar,textoBuscarFecha;
    private Button AgregarNoticia,botonFiltrar;

    private GoogleMap mMap;

    public VerEventosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        datos = new ArrayList<List<String>>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_vereventos, container, false);
        textoBuscarLugar = v.findViewById(R.id.textoBuscarLugar);
        textoBuscarFecha = v.findViewById(R.id.textoBuscarFecha);

        AgregarNoticia = v.findViewById(R.id.AgregarNoticia);
        botonFiltrar = v.findViewById(R.id.botonFiltrar);

        list = (ListView) v.findViewById(R.id.Lugareslistview);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                obtenerFecha();
//            }
//        });

        list.setAdapter(null);
        loadUsers();

        spinnerTipoBuscar = v.findViewById(R.id.spinnerTipoBuscar);
        spinnerBuscarLugarFiltro = v.findViewById(R.id.spinnerBuscarLugarFiltro);

        ArrayAdapter<CharSequence> adapterNoticias = ArrayAdapter.createFromResource(getContext(),R.array.TipoBusquedaNoticia,android.R.layout.simple_spinner_item);
        adapterNoticias.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        String[] letra = {"A","B","C","D","E"};
        spinnerTipoBuscar.setAdapter(adapterNoticias);

        spinnerTipoBuscar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                switch (position){

                    case 0 :

                        textoBuscarLugar.setText("Ingresa el lugar");
                        int indexa = spinnerTipoBuscar.getSelectedItemPosition();
                        textoBuscarLugar.setEnabled(false); //false lo deshabilitas.
                        textoBuscarLugar.setFocusable(false);
                        textoBuscarFecha.setEnabled(true); //false lo deshabilitas.
                        textoBuscarFecha.setFocusable(true);
                        spinnerBuscarLugarFiltro.setEnabled(false);
                        spinnerBuscarLugarFiltro.setFocusable(false);

                        textoBuscarFecha.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                obtenerFecha();
                            }
                        });

                        botonFiltrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!datos.isEmpty()) {
                                    datos.clear();
                                    temp.clear();
                                }
                                loadUsersFiltroFecha(textoBuscarFecha.getText().toString());
                            }
                        });



                        break;



                    case 1:

                        textoBuscarFecha.setText("Seleccione Fecha");
                        textoBuscarLugar.setText("");
                        textoBuscarLugar.setHint("Ingresa el lugar");
                        int indexb = spinnerTipoBuscar.getSelectedItemPosition();
                        textoBuscarLugar.setEnabled(true); //false lo deshabilitas.
                        textoBuscarLugar.setFocusable(true);
                        textoBuscarLugar.setInputType(InputType.TYPE_CLASS_TEXT);
                        textoBuscarLugar.setFocusableInTouchMode(true);

                        textoBuscarFecha.setEnabled(false); //false lo deshabilitas.
                        textoBuscarFecha.setFocusable(false);
                        spinnerBuscarLugarFiltro.setEnabled(false);
                        spinnerBuscarLugarFiltro.setFocusable(false);


                        botonFiltrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Geocoder mGeocoder = new Geocoder(getActivity().getBaseContext());
                                String addressString = textoBuscarLugar.getText().toString();
                                Log.d("FILTRO",textoBuscarLugar.getText().toString());
                                if (!addressString.isEmpty()) {
                                    try {
                                        List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                                        if (addresses != null && !addresses.isEmpty()) {
                                            Address addressResult = addresses.get(0);
                                            LatLng positions = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                                            if (positions != null) {
                                                Log.d("FILTRO", "ENTRE");
                                                Log.d("FILTRO", String.valueOf(positions));

                                                if(!datos.isEmpty()) {
                                                    datos.clear();
                                                    temp.clear();
                                                }
                                                loadUsersFiltroLugar(positions);
                                            }
                                        } else {Toast.makeText(getActivity(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {Toast.makeText(getActivity(), "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
                            }
                        });


                        break;

                    case 2:

                        textoBuscarLugar.setText("Ingresa el lugar");
                        textoBuscarFecha.setText("Seleccione Fecha");

                        int indexc = spinnerTipoBuscar.getSelectedItemPosition();
                        textoBuscarLugar.setEnabled(false); //false lo deshabilitas.
                        textoBuscarLugar.setFocusable(false);
                        textoBuscarFecha.setEnabled(false); //false lo deshabilitas.
                        textoBuscarFecha.setFocusable(false);


                        spinnerBuscarLugarFiltro.setEnabled(true);
                        spinnerBuscarLugarFiltro.setFocusable(true);
                        ArrayAdapter<CharSequence> adapterNoticias = ArrayAdapter.createFromResource(getContext(),R.array.TipoEvento,android.R.layout.simple_spinner_item);
                        adapterNoticias.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinnerBuscarLugarFiltro.setAdapter(adapterNoticias);


                        botonFiltrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!datos.isEmpty()) {
                                    datos.clear();
                                    temp.clear();
                                }

                                loadUsersFiltroTipo(spinnerBuscarLugarFiltro.getSelectedItem().toString());
                            }
                        });

                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AgregarNoticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!datos.isEmpty()) {
                    datos.clear();
                    temp.clear();
                }
                agregarNoticia();
            }
        });


        return v;
    }

    //////////////////////////////////////////////TRAERLUGARES/////////////////////////////////////////////////////////////////////////////////////////////
    public void loadUsers() {
        myRef = database.getReference(PATHLUGARESANFITRION);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    EventosClass noticia = singleSnapshot.getValue(EventosClass.class);
                    temp = new ArrayList<String>();
//                    Log.i(TAG, noticia.toString());
                    temp.add(noticia.getNombre());
//                    Log.i(TAG, "SALI");
                    StorageReference lugarRef = mStorageRef.child(noticia.getPath()).child(noticia.getNombreimagenes().get(0));
                    Log.i(TAG, lugarRef.toString());

                   File localFile = null;

                    try {
                        localFile = File.createTempFile("images_" + noticia.getNombreimagenes().get(0), ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, localFile.toString());

                    lugarRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.i(TAG, "EXITO"+taskSnapshot.getStorage().toString());
                                    list.invalidate();
                                    list.setAdapter((EventosAdapter) list.getAdapter());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i(TAG, "FALLA");
                        }
                    });

                    temp.add(localFile.getPath());
                    //temp.add("");
                    temp.add("Desde: "+noticia.getFechaDesde()+"\nHasta: "+noticia.getFechaHasta()+"\nTipo: " + noticia.getTipo() + "\nDescripcion: \n" + String.valueOf(noticia.getDescripcion()));
//                    temp.add("Descripcion: " + String.valueOf(noticia.getDescripcion()));
                    datos.add(temp);
                }

                list.setAdapter(new EventosAdapter(getContext(), datos));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());
            }
        });

    }
    //////////////////////////////////////////////TRAERLUGARES--/////////////////////////////////////////////////////////////////////////////////////////////

    public void loadUsersFiltroLugar(final LatLng latlng) {

        myRef = database.getReference(PATHLUGARESANFITRION);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    EventosClass noticia = singleSnapshot.getValue(EventosClass.class);
                    temp = new ArrayList<String>();
//                    Log.i(TAG, noticia.toString());
                    temp.add(noticia.getNombre());
//                    Log.i(TAG, "SALI");
                    StorageReference lugarRef = mStorageRef.child(noticia.getPath()).child(noticia.getNombreimagenes().get(0));
                    Log.i(TAG, lugarRef.toString());

                    File localFile = null;

                    try {
                        localFile = File.createTempFile("images_" + noticia.getNombreimagenes().get(0), ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, localFile.toString());

                    lugarRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.i(TAG, "EXITO"+taskSnapshot.getStorage().toString());
                                    list.invalidate();
                                    list.setAdapter((EventosAdapter) list.getAdapter());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i(TAG, "FALLA");
                        }
                    });


                    Log.d("FILTRO","busqueda latitud primer: " +String.valueOf(latlng.latitude));

                    if((latlng.latitude - (noticia.getLatitude())) >= 0 &&  (latlng.latitude - (noticia.getLatitude())) <= 0.04){
                        Log.d("FILTRO","busqueda latitud 1: " +String.valueOf((latlng.latitude - (noticia.getLatitude()))));

                        temp.add(localFile.getPath());
                        //temp.add("");
                        temp.add("Desde: "+noticia.getFechaDesde()+"\nHasta: "+noticia.getFechaHasta()+"\nTipo: " + noticia.getTipo() + "\nDescripcion: \n" + String.valueOf(noticia.getDescripcion()));
//                    temp.add("Descripcion: " + String.valueOf(noticia.getDescripcion()));
                        datos.add(temp);
                    }
                }
                list.setAdapter(new EventosAdapter(getContext(), datos));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());
            }
        });

    }


    public void loadUsersFiltroFecha(final String fecha) {

        myRef = database.getReference(PATHLUGARESANFITRION);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    EventosClass noticia = singleSnapshot.getValue(EventosClass.class);
                    temp = new ArrayList<String>();
//                    Log.i(TAG, noticia.toString());
                    temp.add(noticia.getNombre());
//                    Log.i(TAG, "SALI");
                    StorageReference lugarRef = mStorageRef.child(noticia.getPath()).child(noticia.getNombreimagenes().get(0));
                    Log.i(TAG, lugarRef.toString());

                    File localFile = null;

                    try {
                        localFile = File.createTempFile("images_" + noticia.getNombreimagenes().get(0), ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, localFile.toString());

                    lugarRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.i(TAG, "EXITO"+taskSnapshot.getStorage().toString());
                                    list.invalidate();
                                    list.setAdapter((EventosAdapter) list.getAdapter());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i(TAG, "FALLA");
                        }
                    });


                    Date date1 = null;
                    Date date2 = null;
                    Date date3 = null;



                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Para declarar valores en nuevos objetos date, usa el mismo formato date que usaste al crear las fechas
                    try {
                        date1= sdf.parse(fecha); //date1 es el 23 de febrero de 1995
                        date2= sdf.parse(noticia.getFechaDesde());
                        date3= sdf.parse(noticia.getFechaHasta());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//                    Log.d("FILTRO","Fecha 1: " +date1);
//                    Log.d("FILTRO","Fecha 2: " +date2);
//                    Log.d("FILTRO","Fecha 3: " +date3);





                    if((date1.after(date2)&&date1.before(date3))  || date1.equals(date2) || date1.equals(date3) ){

                        Log.d("FILTRO","Resultado: ENTRE");
                        temp.add(localFile.getPath());
                        //temp.add("");
                        temp.add("Desde: "+noticia.getFechaDesde()+"\nHasta: "+noticia.getFechaHasta()+"\nTipo: " + noticia.getTipo() + "\nDescripcion: \n" + String.valueOf(noticia.getDescripcion()));
//                    temp.add("Descripcion: " + String.valueOf(noticia.getDescripcion()));
                        datos.add(temp);
                    }
                }
                list.setAdapter(new EventosAdapter(getContext(), datos));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());
            }
        });

    }


    public void loadUsersFiltroTipo(final String tipo) {

        myRef = database.getReference(PATHLUGARESANFITRION);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    EventosClass noticia = singleSnapshot.getValue(EventosClass.class);
                    temp = new ArrayList<String>();
//                    Log.i(TAG, noticia.toString());
                    temp.add(noticia.getNombre());
//                    Log.i(TAG, "SALI");
                    StorageReference lugarRef = mStorageRef.child(noticia.getPath()).child(noticia.getNombreimagenes().get(0));
                    Log.i(TAG, lugarRef.toString());

                    File localFile = null;

                    try {
                        localFile = File.createTempFile("images_" + noticia.getNombreimagenes().get(0), ".png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, localFile.toString());

                    lugarRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.i(TAG, "EXITO"+taskSnapshot.getStorage().toString());
                                    list.invalidate();
                                    list.setAdapter((EventosAdapter) list.getAdapter());

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i(TAG, "FALLA");
                        }
                    });


                    if(tipo.equals(noticia.getTipo())){

                        Log.d("FILTRO","Resultado: ENTRE TIPO");
                        temp.add(localFile.getPath());
                        //temp.add("");
                        temp.add("Desde: "+noticia.getFechaDesde()+"\nHasta: "+noticia.getFechaHasta()+"\nTipo: " + noticia.getTipo() + "\nDescripcion: \n" + String.valueOf(noticia.getDescripcion()));
//                    temp.add("Descripcion: " + String.valueOf(noticia.getDescripcion()));
                        datos.add(temp);
                    }
                }
                list.setAdapter(new EventosAdapter(getContext(), datos));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());
            }
        });

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
//                lugar.setFechaDesde(diaFormateado + BARRA + mesFormateado + BARRA + year);
                textoBuscarFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void agregarNoticia() {
        AgregarEventoFragment agregarlugar = new AgregarEventoFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameDinamico, agregarlugar);
        transaction.addToBackStack("agregarFragagregarnoticia");
        transaction.commit();
    }



}
