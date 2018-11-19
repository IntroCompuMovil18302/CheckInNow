package com.checkinnow.checkinnow.SesionIniciada;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.checkinnow.checkinnow.Module;
import com.checkinnow.checkinnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import Modelo.LugarClass;


public class Gmap2Fragment extends Fragment implements OnMapReadyCallback,  DatePickerDialog.OnDateSetListener {


    EditText textoBuscarFecha;
    Button botonGo;
    Button buscarLocasion;

    DatabaseReference databaseReference;
    ListView listshow;
    Button GO;
    ArrayList<String> arrList= new ArrayList<>();
    ArrayAdapter<String> arrAdp;
    Module module;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";


    double nuevoLugar;
    private static double latitudDou;
    private static double longitudDou;
    private static double altitudDou;

    private Marker lastmarker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gmaps2, container,false);

        textoBuscarFecha = (EditText) v.findViewById(R.id.textoBuscar);
        botonGo = (Button) v.findViewById(R.id.botonGO);

        module= new Module();
        databaseReference=FirebaseDatabase.getInstance().getReference("anfitrion/idusuario");
        listshow= (ListView) v.findViewById(R.id.listView);
        arrAdp= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrList);
        listshow.setAdapter(arrAdp);
        //Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textoBuscarFecha = (EditText) view.findViewById(R.id.textoBuscar);
        botonGo = (Button) view.findViewById(R.id.botonGO);
        buscarLocasion = (Button) view.findViewById(R.id.txtbuscar);

        String texto =textoBuscarFecha.getText().toString();



        textoBuscarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });



        botonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        buscarLocasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapas();
            }
        });





    }





    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);

        googleMap.setMapStyle(MapStyleOptions
                .loadRawResourceStyle(getContext(), R.raw.mapa));

        Criteria criteria = new Criteria();
        String context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(context);
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        LatLng locations = new LatLng(4.0151969, -74.1989402);
        lastmarker = googleMap.addMarker(new MarkerOptions().position(locations).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 5));

        lastmarker.remove();



        LatLng myPosition = new LatLng(latitude, longitude);



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value= dataSnapshot.getValue(LugarClass.class).toString();

                LatLng marker = new LatLng(dataSnapshot.getValue(LugarClass.class).getLatitude(),dataSnapshot.getValue(LugarClass.class).getLongitud());

//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 10));

                googleMap.addMarker(new MarkerOptions().title(dataSnapshot.getValue(LugarClass.class).getNombre()).position(marker));

                arrList.add(value);
                arrAdp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        listshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //UpdateInput(arrList.get(position),position);
                module.setGvalue_id(arrList.get(position));
                module.setGvalue_Name(arrList.get(position));
                module.setGvalue_Latitude(arrList.get(position));
                module.setGvalue_Longitude(arrList.get(position));
                //Toast.makeText(Showdata.this,arrList.get(position),Toast.LENGTH_LONG).show();
            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14));

        googleMap.addMarker(new MarkerOptions().title("Su ubicación Actual!")
                .position(myPosition)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        googleMap.addCircle(
                new CircleOptions()
                        .center(myPosition)
                        .radius(2000) //metros
                        .strokeWidth(10)
                        .strokeColor(Color.RED)
                        .fillColor(Color.rgb(70,150,50))
                        .clickable(true));
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(false);


    }


    public void mapas(){
        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
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

}
