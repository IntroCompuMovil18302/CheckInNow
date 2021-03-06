package com.checkinnow.checkinnow.Huesped;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.List;

import Modelo.LugarAdapter;
import Modelo.LugarClass;
import Modelo.Reserva;

import static Modelo.ContantesClass.PATHLUGARES;
import static Modelo.ContantesClass.PATHRESERVAS;
import static Modelo.ContantesClass.PATHRESERVASUSERFINAL;
import static Modelo.ContantesClass.PATHRESERVASUSERINIT;
import static Modelo.ContantesClass.TAG;
import static Modelo.ContantesClass.Uid;


public class VesreservasFragment extends Fragment {

    private List<List<String>> datos;
    private List<String> temp;
    private View v;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference ref;
    private StorageReference mStorageRef;
    private ListView list;
    private List<Reserva> lugares;

    public VesreservasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_vesreservas, container, false);
        list = (ListView) v.findViewById(R.id.Reservaslistview);

        datos = new ArrayList<List<String>>();
        lugares = new ArrayList<Reserva>();

        loadReservas();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trazarruta(position);
            }
        });


        return v;
    }

    private void loadReservas() {
        myRef = database.getReference(PATHRESERVASUSERINIT + Uid + PATHRESERVASUSERFINAL);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    final Reserva resev = singleSnapshot.getValue(Reserva.class);
                    lugares.add(resev);
                    temp = new ArrayList<String>();
                    temp.clear();


                    StorageReference lugarRef = mStorageRef.child(resev.getLugar().getPath()).child(resev.getLugar().getNombreimagenes().get(0));
                    File localFile = null;

                    try {
                        localFile = File.createTempFile("images_" + resev.getLugar().getNombreimagenes().get(0), ".png");
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

                    temp.add(resev.getLugar().getNombre());
                    temp.add(localFile.getPath());
                    temp.add("Tipo: " + resev.getLugar().getTipo() + "\nValor: " +
                            String.valueOf(resev.getLugar().getValor()) + " " +
                            resev.getLugar().getMoneda() +
                            "\nRESERVADO: " + resev.getFechaorigen() + " - " + resev.getFechafin());
                    datos.add(temp);

/*
                    ref = database.getReference(PATHLUGARES + resev.getLugarid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            LugarClass lugar = dataSnapshot.getValue(LugarClass.class);
                            StorageReference lugarRef = mStorageRef.child(lugar.getPath()).child(lugar.getNombreimagenes().get(0));
                            Log.i(TAG, "-------"+lugar.toString());
                            lugares.add(lugar);
                            File localFile = null;

                            try {
                                localFile = File.createTempFile("images_" + lugar.getNombreimagenes().get(0), ".png");
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

                            temp.add(lugar.getNombre());
                            temp.add(localFile.getPath());


                            temp.add("Tipo: " + lugar.getTipo() + "\nValor: " +
                                    String.valueOf(lugar.getValor()) + " " +
                                    lugar.getMoneda() +
                                    "\nRESERVADO: " + resev.getFechaorigen() + " - " + resev.getFechafin());
                            datos.add(temp);
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });*/
                }
                //list.invalidate();
                list.setAdapter(new LugarAdapter(v.getContext(), datos));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "error en la consulta", databaseError.toException());

            }
        });
    }

    private void trazarruta(int position) {

        Intent intento = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("LUGAR", lugares.get(position).getLugar());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =  fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        RutaFragment fragment2 = new RutaFragment();
        fragment2.setArguments(bundle);
        ft.replace(R.id.frameDinamico, fragment2);
        ft.addToBackStack(null);
        ft.commit();
    }


}
