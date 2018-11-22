package com.checkinnow.checkinnow.Huesped;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.checkinnow.checkinnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Modelo.LugarClass;
import Modelo.Reserva;

import static Modelo.ContantesClass.PATHLUGARES;
import static Modelo.ContantesClass.PATHRESERVAS;
import static Modelo.ContantesClass.PATHRESERVASUSERFINAL;
import static Modelo.ContantesClass.PATHRESERVASUSERINIT;
import static Modelo.ContantesClass.TAG;
import static Modelo.ContantesClass.Uid;

public class infolugarReservaFragment extends Fragment implements OnMapReadyCallback {


    MapView mMapView;

    View v;
    //File localfile;
    List<File> files;
    Button botonreserva;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Marker lastmarker;
    private List<Marker> puestos;
    private SupportMapFragment mapFragment;
    private LugarClass lugar;
    private Reserva reserva;
    private Button atras;
    private GoogleMap mMap;
    private ImageView unic;
    private ImageView unic2;
    private ImageView unic3;
    private ImageView unic4;
    private LinearLayout container;

    private TextView lugarname;
    private TextView tipo;
    private TextView hab;
    private TextView cam;
    private TextView ban;
    private TextView estac;
    private TextView masc;
    private TextView moneda;
    private TextView val;
    private TextView de;
    private TextView a;
    private String key;

    public infolugarReservaFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference( PATHRESERVAS );
        this.key = myRef.push().getKey();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            lugar = (LugarClass) bundle.getSerializable("LUGAR");
            Log.i(TAG, lugar.toString());
            reserva = (Reserva) bundle.getSerializable("RESERVA");
            reserva.setLugar(lugar);
            Log.i(TAG, reserva.toString());

        }

        files = new ArrayList<File>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_infolugar_reserva, container, false);
        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        atras = v.findViewById(R.id.bntatras);

        lugarname = v.findViewById(R.id.lugarname);
        tipo = v.findViewById(R.id.tipo);
        hab = v.findViewById(R.id.textViewhab);
        cam = v.findViewById(R.id.textViewcam);
        ban = v.findViewById(R.id.textViewban);
        estac = v.findViewById(R.id.textViewestac);
        masc = v.findViewById(R.id.textViewmasc);
        moneda = v.findViewById(R.id.textViewmon);
        val = v.findViewById(R.id.textViewval);
        de = v.findViewById(R.id.textViewde);
        a = v.findViewById(R.id.textViewa);
        unic = v.findViewById(R.id.unica);
        unic2 = v.findViewById(R.id.unica2);
        unic3 = v.findViewById(R.id.unica3);
        unic4 = v.findViewById(R.id.unica4);
        botonreserva = v.findViewById(R.id.buttonReservar);


        loadinfo();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras();
            }
        });

        botonreserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservar();
            }
        });
        return v;
    }

    private void reservar() {

        myRef = database.getReference(PATHRESERVAS + this.key );
        myRef.setValue(reserva);
        myRef = database.getReference(PATHRESERVASUSERINIT+Uid+PATHRESERVASUSERFINAL+key);
        myRef.setValue(reserva);
        getFragmentManager().popBackStack();

    }


    @SuppressLint("SetTextI18n")
    private void loadinfo() {
        lugarname.setText(lugarname.getText().toString() + " " + lugar.getNombre());
        tipo.setText(tipo.getText().toString() + " " + lugar.getTipo());
        hab.setText(hab.getText().toString() + " " + String.valueOf(lugar.getHabitaciones()));
        cam.setText(cam.getText().toString() + " " + String.valueOf(lugar.getCamas()));
        ban.setText(ban.getText().toString() + " " + String.valueOf(lugar.getBanos()));


        if (lugar.isEstacionamiento()) {
            estac.setText(estac.getText().toString() + " " + "SI");
        } else {
            estac.setText(estac.getText().toString() + " " + "NO");
        }

        if (lugar.isMascota()) {
            masc.setText(masc.getText().toString() + " " + "SI");
        } else {
            masc.setText(masc.getText().toString() + " " + "NO");
        }


        moneda.setText(lugar.getMoneda());
        val.setText(String.valueOf(lugar.getValor()));
        de.setText(reserva.getFechaorigen());
        a.setText(reserva.getFechafin());

        for (String img : lugar.getNombreimagenes()) {

        StorageReference lugarRef = mStorageRef.child(lugar.getPath()).child(img);
        Log.i(TAG, lugarRef.toString());

            File localfile = null;

            try {
                //localfile = File.createTempFile("images_" + lugar.getNombreimagenes().get(0), ".png");
                localfile = File.createTempFile("images_" + img, ".png");
                Log.i(TAG,localfile.toString());
                files.add(localfile);
                Log.i(TAG,files.get(files.size()-1).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(TAG, localfile.toString());

            lugarRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.i(TAG, "EXITO" + taskSnapshot.getStorage().toString());
                            refresh();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "FALLA");
                }
            });


        }

    }

    private void refresh() {
        if (files.get(0).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap myBitmap = BitmapFactory.decodeFile(files.get(0).getPath(), options);
            unic.setImageBitmap(myBitmap);
        } else {
            Log.i(TAG, "NO encontro la imagen el adapter");
        }
        if (files.get(1).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap myBitmap2 = BitmapFactory.decodeFile(files.get(1).getPath(), options);
            unic2.setImageBitmap(myBitmap2);
        } else {
            Log.i(TAG, "NO encontro la imagen el adapter");
        }
        if (files.get(2).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap myBitmap = BitmapFactory.decodeFile(files.get(2).getPath(), options);
            unic3.setImageBitmap(myBitmap);
        } else {
            Log.i(TAG, "NO encontro la imagen el adapter");
        }
        if (files.get(3).exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize =2;

            Bitmap myBitmap = BitmapFactory.decodeFile(files.get(3).getPath(), options);
            unic4.setImageBitmap(myBitmap);
        } else {
            Log.i(TAG, "NO encontro la imagen el adapter");
        }
    }


    private void atras() {
        int count = getFragmentManager().getBackStackEntryCount();

        getFragmentManager().popBackStack();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(v.getContext(), R.raw.mapa));

        LatLng location = new LatLng(4.0151969, -74.1989402);
        lastmarker = mMap.addMarker(new MarkerOptions().position(location).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
        lastmarker.remove();

        LatLng lugartemp = new LatLng(lugar.getLatitude(), lugar.getLongitud());
        mMap.addMarker(new MarkerOptions().position(lugartemp).title(lugar.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugartemp, 15));

    }

}
