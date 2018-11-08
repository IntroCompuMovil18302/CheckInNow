package com.checkinnow.checkinnow.SesionIniciada;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.checkinnow.checkinnow.Module;
import com.checkinnow.checkinnow.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class facebookfra extends Fragment implements OnMapReadyCallback {
    DatabaseReference databaseReference;
    ListView listshow;
    Button GO;
    ArrayList <String> arrList= new ArrayList<>();
    ArrayAdapter <String> arrAdp;
    Module module;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    double nuevoLugar;
    private static double latitudDou;
    private static double longitudDou;
    private static double altitudDou;


    public facebookfra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facebookfra, container, false);
        GO = v.findViewById(R.id.botonBuscar);
        module= new Module();
        databaseReference=FirebaseDatabase.getInstance().getReference("locations");
//        listshow= (ListView) v.findViewById(R.id.listView);
        arrAdp= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrList);
        listshow.setAdapter(arrAdp);
        //Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();

        GO.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {

            }
        });





        return v;
    }

    public void buscarFecha(){

    }

    public void buscarLugar(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value= dataSnapshot.getValue(MyLocation.class).toStringLugar();
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
    }


    @SuppressLint("MissingPermission")
    public void mostrarMapa(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new
                OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("LOCATION", "onSuccess location");
                        if (location != null) {
//                            Log.i(" LOCALIZACION ", "Latitud: " + location.getLatitude());
//                            Log.i(" LOCALIZACION ", "Longitud: " + location.getLongitude());
//                            Log.i(" LOCALIZACION ", "Altitud: " + location.getAltitude());
                            latitudDou = location.getLatitude();
                            longitudDou = location.getLongitude();
                            altitudDou = location.getAltitude();
                            Log.i(" LOCALIZACION ", "Res 1 Longitud: " + longitudDou + "latitud: "+latitudDou);
                            onMapReady(mMap);
                        }
                    }
                });

    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        Log.i(" LOCALIZACION ", "Res 2 Longitud: " + longitudDou + "latitud: "+latitudDou);
        LatLng bogota = new LatLng(latitudDou,longitudDou);

        Log.i(" LOCALIZACION ", "BOGOTA: " + bogota);
        mMap.addMarker(new MarkerOptions().position(bogota).title("Marcador en Bogotá"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        Marker bogotaAzul = mMap.addMarker(new MarkerOptions()
                .position(bogota)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        LatLng marc1 = new LatLng(4.639199,-74.098028);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marc1));
        mMap.addMarker(new MarkerOptions().position(marc1)
                .title("Tryp")
                .snippet("Costo: ____"));

        LatLng marc2 = new LatLng(4.655053,-74.084582);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marc2));
        mMap.addMarker(new MarkerOptions().position(marc2)
                .title("Complejo Acuatico")
                .snippet("Costo: ____"));

        LatLng marc3 = new LatLng(4.661188,-74.098602);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marc3));
        mMap.addMarker(new MarkerOptions().position(marc3)
                .title("CUR Compensar 68")
                .snippet("Costo: ____"));

        LatLng marc4 = new LatLng(4.642299,-74.079708);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marc4));
        mMap.addMarker(new MarkerOptions().position(marc4)
                .title("El Cubo")
                .snippet("Costo: ____"));

        LatLng marc5 = new LatLng(4.643092,-74.098353);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marc5));
        mMap.addMarker(new MarkerOptions().position(marc5)
                .title("Hyatt")
                .snippet("Costo: ____"));

    }


}
