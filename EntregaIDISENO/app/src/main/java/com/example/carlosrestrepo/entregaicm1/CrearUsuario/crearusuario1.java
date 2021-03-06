package com.example.carlosrestrepo.entregaicm1.CrearUsuario;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.carlosrestrepo.entregaicm1.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;



public class crearusuario1 extends Fragment implements DatePickerDialog.OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private Button Siguiente;
    private ImageButton botonParaFecha;
    EditText TextoFecha;
    CircleImageView ImagenCircular;

    EditText NombresRegistro;
    EditText ApellidosRegistros;
    EditText CorreoRegistro;
    EditText ContaseñaRegistro;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    String StringTextoFecha;
    String StringNombresRegistro;
    String StringApellidosRegistros;
    String StringCorreoRegistro;
    String StringContaseñaRegistro;

    public crearusuario1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_crearusuario1,container,false);
        TextoFecha = (EditText) v.findViewById(R.id.FechaNacimiento);
        NombresRegistro = (EditText) v.findViewById(R.id.NombresRegistro);
        ApellidosRegistros = (EditText) v.findViewById(R.id.ApellidosRegistros);
        CorreoRegistro = (EditText) v.findViewById(R.id.CorreoRegistro);
        ContaseñaRegistro = (EditText) v.findViewById(R.id.ContaseñaRegistro);
        Siguiente = (Button) v.findViewById(R.id.Siguiente);
        Siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                StringTextoFecha=TextoFecha.getText().toString();
                StringNombresRegistro=NombresRegistro.getText().toString();
                StringApellidosRegistros=ApellidosRegistros.getText().toString();
                StringCorreoRegistro=CorreoRegistro.getText().toString();
                StringContaseñaRegistro=ContaseñaRegistro.getText().toString();

                fragmentoSiguiente(StringTextoFecha,StringNombresRegistro,StringApellidosRegistros,StringCorreoRegistro,StringContaseñaRegistro);
            }
        });


        botonParaFecha = (ImageButton) v.findViewById(R.id.ImagenFechaNacimiento);
        botonParaFecha.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                obtenerFecha();

            }
        });

        ImagenCircular = (CircleImageView) v.findViewById(R.id.imagenPerfil);
        ImagenCircular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CargarImagen();

            }
        });


        return v;
    }



    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {
        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
        final int mesActual = month + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
        //Muestro la fecha con el formato deseado
        TextoFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity());
        onDateSet(recogerFecha,anio,mes,dia);
        //Muestro el widget
        recogerFecha.show();

    }

    public void CargarImagen(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());

        dialogo.setTitle("Cargar Foto para Perfil");
        dialogo.setMessage("Acceder a galeria de fotos");
        dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogo.create();

        dialogo.show();
    }

    public void fragmentoSiguiente(String stringTextoFecha, String stringNombresRegistro, String stringApellidosRegistros, String stringCorreoRegistro, String stringContaseñaRegistro){

        if(stringNombresRegistro.isEmpty() || stringTextoFecha.isEmpty() || stringApellidosRegistros.isEmpty() || stringContaseñaRegistro.isEmpty()||stringCorreoRegistro.isEmpty()){
            Toast.makeText(getActivity(),"Hace falta llenar información", Toast.LENGTH_LONG).show();
        }else {

            crearusuario2 crear2 = new crearusuario2();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.dinamicoCrear, crear2);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }


}
