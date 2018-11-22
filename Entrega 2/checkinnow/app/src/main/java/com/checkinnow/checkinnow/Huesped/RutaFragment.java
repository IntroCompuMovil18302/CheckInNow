package com.checkinnow.checkinnow.Huesped;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.maps.android.PolyUtil;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import Modelo.LugarClass;
import Modelo.Reserva;

import static Modelo.ContantesClass.LOCATION_PERMISSION;
import static Modelo.ContantesClass.TAG;
import static android.app.Activity.RESULT_OK;

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
    private Marker ira;
    private LugarClass lugar;
    private TextView donde;
    private Button buttonatrass;

    public RutaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            lugar = (LugarClass) bundle.getSerializable("LUGAR");
            Log.i(TAG, lugar.toString());
        }
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
        donde = v.findViewById(R.id.adonde);
        buttonatrass = v.findViewById(R.id.buttonatrass);
        //Log.i(TAG,lugar.toString());
        donde.setText(lugar.getNombre());
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

                        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+newmark.getPosition().latitude+","+newmark.getPosition().longitude+"&destination="+lastmark.getPosition().latitude+","+lastmark.getPosition().longitude+ "&key=" + "AIzaSyCUxvK1V1L76Z5LwY0trWvNEaT--CJlBYc";
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.i("LOCATION", response);
                                    jso = new JSONObject(response);
                                    trazarRuta(jso);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(stringRequest);

                    } else {

                        LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                        oldmark.remove();
                        newmark = mMap.addMarker(new MarkerOptions().position(user).title("Usted").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.usermark)));
                        oldmark = newmark;
                    }
                }
            }
        };

        buttonatrass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atras();
            }
        });

        return v;
    }

    private void atras() {
        int count = getFragmentManager().getBackStackEntryCount();

        getFragmentManager().popBackStack();

    }

    private void trazarRuta(JSONObject jso) {

        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){
                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.BLUE).width(10));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(v.getContext(), R.raw.mapa));
        LatLng punto = new LatLng(lugar.getLatitude(), lugar.getLongitud());
        lastmark = mMap.addMarker(new MarkerOptions().position(punto).title(lugar.getNombre()).snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder322)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 5));


    }

}
