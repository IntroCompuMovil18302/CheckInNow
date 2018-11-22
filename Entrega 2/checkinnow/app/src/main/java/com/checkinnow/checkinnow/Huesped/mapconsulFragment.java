package com.checkinnow.checkinnow.Huesped;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.checkinnow.checkinnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Modelo.LugarClass;
import Modelo.Reserva;

import static Modelo.ContantesClass.PATHLUGARES;
import static Modelo.ContantesClass.TAG;
import static Modelo.ContantesClass.Uid;


public class mapconsulFragment extends Fragment implements OnMapReadyCallback {

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final double RADIUS_OF_EARTH_KM = 6371;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    MapView mMapView;

    View v;
    Button botonseleccion;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private GoogleMap mMap;
    private Marker lastmarker;
    private List<Marker> puestos;
    private List<LugarClass> lugares;
    private SupportMapFragment mapFragment;
    private EditText origen;
    private EditText fin;
    private Circle lascircle;


    public mapconsulFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        puestos = new ArrayList<Marker>();
        lugares = new ArrayList<LugarClass>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_mapconsul, container, false);

       // botonseleccion = v.findViewById(R.id.buttonBuscar);
        origen = v.findViewById(R.id.fechaorig);
        fin = v.findViewById(R.id.fechafin);


        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView) v.findViewById(R.id.map2);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(origen);
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fin);
            }
        });


        botonseleccion.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //regresar();
            }
        });


        return v;
    }

    private void obtenerFecha(final EditText edit) {

        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                edit.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void irinfo(Marker marker) {
        if (lastmarker != null) {

            Intent intento = new Intent();
            Bundle bundle = new Bundle();

            for (LugarClass lug : lugares) {
                if (marker.getPosition().latitude == lug.getLatitude() &&
                        marker.getPosition().longitude == lug.getLongitud()) {
                    bundle.putSerializable("LUGAR", lug);
                    Reserva reserva = new Reserva(lug.getID(),Uid,origen.getText().toString(),fin.getText().toString());
                    bundle.putSerializable("RESERVA",reserva);
                    intento.putExtra("bundle", bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft =  fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    infolugarReservaFragment fragment2 = new infolugarReservaFragment();
                    fragment2.setArguments(bundle);
                    ft.replace(R.id.frameDinamico, fragment2);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            }


        } else {
            Toast toast = Toast.makeText(getContext(), "No a seleccionado ningún punto aun.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

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
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setMapStyle(MapStyleOptions
                .loadRawResourceStyle(v.getContext(), R.raw.mapa));

        LatLng location = new LatLng(4.0151969, -74.1989402);
        lastmarker = mMap.addMarker(new MarkerOptions().position(location).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));

        lastmarker.remove();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onMarkerClick(Marker marker) {

                irinfo(marker);
                return true;
            }
        });


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                seleccion(latLng);
            }
        });

    }

    private void seleccion(LatLng latLng) {
        if (lastmarker != null) {
            lastmarker.remove();

        }
        if (lascircle != null) {
            lascircle.remove();
            for (Marker mark : puestos) {
                mark.remove();
            }
        }
        lastmarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.usermark)));
        //latlong.setText("Lat: " + latLng.latitude + " Long: " + latLng.longitude);
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(2000) //metros
                .strokeWidth(10)
                .strokeColor(Color.BLACK)
                .fillColor(Color.argb(128, 44, 132, 132))
                .clickable(true);
        lascircle = mMap.addCircle(circleOptions);

        cargarPuntosCercanos(latLng);
    }

    private void cargarPuntosCercanos(final LatLng latLng) {

        myRef = database.getReference(PATHLUGARES);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    LugarClass lug = singleSnapshot.getValue(LugarClass.class);
                    Log.i(TAG, "LUGAR:\n " + lug.toString());
                    double dist;
                    dist = distancepoint(latLng.latitude, latLng.longitude, lug.getLatitude(), lug.getLongitud());
                    Log.i(TAG, "LU...........GAR:\n " + dist);
                    if (dist < 2) {
                        Marker mark;
                        LatLng lugartemp = new LatLng(lug.getLatitude(), lug.getLongitud());
                        mark = mMap.addMarker(new MarkerOptions().position(lugartemp).title(lug.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
                        puestos.add(mark);
                        lugares.add(lug);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());
            }
        });
    }

    public double distancepoint(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result * 100.0) / 100.0;
    }


}
