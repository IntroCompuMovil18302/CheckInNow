package com.checkinnow.checkinnow.Anfitrion;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.checkinnow.checkinnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaSeleccionFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    View v;
    Button botonseleccion;
    private GoogleMap mMap;
    private Marker lastmarker;
    private SupportMapFragment mapFragment;
    private TextView latlong;

    public MapaSeleccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_mapa_seleccion, container, false);

        latlong = v.findViewById(R.id.latlong);
        botonseleccion = v.findViewById(R.id.Seleccionarbutton);

        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        botonseleccion.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                regresar();
            }
        });


        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void regresar() {
        if (lastmarker != null) {

            Intent intento = new Intent();
            Bundle bundle = new Bundle();

            bundle.putDouble("lat", lastmarker.getPosition().latitude);
            bundle.putDouble("long", lastmarker.getPosition().longitude);

            intento.putExtra("bundle", bundle);

            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    Activity.RESULT_OK,
                    intento
            );
            getFragmentManager().popBackStack();

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
                .loadRawResourceStyle(getContext(), R.raw.mapa));

        LatLng location = new LatLng(4.0151969, -74.1989402);
        lastmarker = mMap.addMarker(new MarkerOptions().position(location).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));

        lastmarker.remove();



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
        lastmarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Aqui").icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
        latlong.setText("Lat: " + latLng.latitude + " Long: " + latLng.longitude);
    }
}
