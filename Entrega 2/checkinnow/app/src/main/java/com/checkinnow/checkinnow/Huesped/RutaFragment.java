package com.checkinnow.checkinnow.Huesped;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.checkinnow.checkinnow.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;

import static Modelo.ContantesClass.LOCATION_PERMISSION;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RutaFragment extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    View v;
    private GoogleMap mMap;

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private List<String> listalocations;
    private JSONObject jso;

    private FusedLocationProviderClient mFusedLocationClient;
    private static int voy;

    private Marker oldmark;
    private Marker newmark;
    private Marker lastmark;

    public RutaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ruta, container, false);
        // Inflate the layout for this fragment
        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView) v.findViewById(R.id.rutamap);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        voy = 0;

        requestPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,
                "Se necesita acceder a la ubicacion", LOCATION_PERMISSION);

        mLocationRequest = createLocationRequest();

        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        mLocationCallback = new LocationCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Log.i("LOCATION", "Location update in the callback: " + location);
                if (location != null && mMap != null) {
                    if (voy == 0) {
                        LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                        oldmark = mMap.addMarker(new MarkerOptions().position(user).title("Usted").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.usermark)));
                        newmark = oldmark;
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(19));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
                        voy++;

                    } else {
                        LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                        oldmark.remove();
                        newmark = mMap.addMarker(new MarkerOptions().position(user).title("Usted").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.usermark)));
                        oldmark = newmark;
                    }
                }
            }
        };

        return v;
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(5000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private void requestPermission(Activity context, String permission, String explanation, int requestId) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, explanation, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, requestId);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (resultCode == RESULT_OK) {
                    startLocationUpdates(); //Se encendió la localización!!!
                } else {
                    Toast.makeText(getContext(),
                            "Sin acceso a localización, hardware deshabilitado!",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        stopLocationUpdates();
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
        startLocationUpdates();
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
        //lastmarker = mMap.addMarker(new MarkerOptions().position(location).title("Aqui"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
        //lastmarker.remove();
    }

}
