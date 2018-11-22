package com.checkinnow.checkinnow.Huesped;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.checkinnow.checkinnow.R;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Modelo.LugarAdapter;
import Modelo.LugarClass;

import static Modelo.ContantesClass.PATHLUGARES;
import static Modelo.ContantesClass.TAG;


public class consultarFragment extends Fragment {

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final double RADIUS_OF_EARTH_KM = 6371;
    private static final double ARRIBADERLAT = 14.69969;
    private static final double ARRIBADERLONG = -66.75442;
    private static final double ABAJOIZQLAT = -4.87688;
    private static final double ABAJOIZQLONG = -80.50998;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    List<List<String>> datos;
    List<String> temp;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private View v;
    private ListView list;
    private EditText ori;
    private EditText fi;
    private EditText lugaText;
    private Button filtrar;
    private double lat;
    private double lon;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public consultarFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
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

        v = inflater.inflate(R.layout.fragment_consultar, container, false);

        list = v.findViewById(R.id.listafiltro);
        ori = v.findViewById(R.id.ori);
        fi = v.findViewById(R.id.fi);
        lugaText = v.findViewById(R.id.lugartext);
        filtrar = v.findViewById(R.id.botonfiltrar);

        ori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(ori);
            }
        });

        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fi);
            }
        });

        filtrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                refreshlist();
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void refreshlist() {
        Geocoder mGeocoder = new Geocoder(getContext());
        datos = new ArrayList<List<String>>();
        try {

            List<Address> addresses = mGeocoder.getFromLocationName(lugaText.getText().toString(), 1, ABAJOIZQLAT, ABAJOIZQLONG, ARRIBADERLAT, ARRIBADERLONG);
            Log.d(TAG, addresses.get(0).toString());
            lat = addresses.get(0).getLatitude();
            lon = addresses.get(0).getLongitude();

            myRef = database.getReference(PATHLUGARES);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        LugarClass lug = singleSnapshot.getValue(LugarClass.class);
                        Log.i(TAG, "LUGAR:\n " + lug.toString());
                        double dist;
                        dist = distancepoint(lat, lon, lug.getLatitude(), lug.getLongitud());
                        Log.i(TAG, "LU...........GAR:\n " + dist);
                        temp = new ArrayList<String>();
                        if (dist < 11) {

                            StorageReference lugarRef = mStorageRef.child(lug.getPath()).child(lug.getNombreimagenes().get(0));
                            File localFile = null;

                            try {
                                localFile = File.createTempFile("images_" + lug.getNombreimagenes().get(0), ".png");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.i(TAG, localFile.toString());

                            lugarRef.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Log.i(TAG, "EXITO" + taskSnapshot.getStorage().toString());
                                            list.invalidate();
                                            list.setAdapter((LugarAdapter) list.getAdapter());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.i(TAG, "FALLA");
                                }
                            });

                            temp.add(lug.getNombre());
                            temp.add(localFile.getPath());
                            temp.add("Tipo: " + lug.getTipo() + "\nValor: " +
                                    String.valueOf(lug.getValor()) + " " +
                                    lug.getMoneda());
                            datos.add(temp);
                        }
                    }
                    list.setAdapter(new LugarAdapter(v.getContext(), datos));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "error en la consulta", databaseError.toException());
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
