package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LugarClass implements Serializable {

    private double latitude;
    private double longitud;
    private double valor;
    private String nombre;
    private String tipo;
    private String ID;
    private String path;
    private List<String> nombreimagenes;
    private int camas;
    private int habitaciones;
    private int banos;
    private boolean estacionamiento;
    private boolean mascota;
    private String moneda;

    @Override
    public String toString() {
        return "LugarClass{" +
                "latitude=" + latitude +
                ", longitud=" + longitud +
                ", valor=" + valor +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ID='" + ID + '\'' +
                ", path='" + path + '\'' +
                ", nombreimagenes=" + nombreimagenes +
                ", camas=" + camas +
                ", habitaciones=" + habitaciones +
                ", banos=" + banos +
                ", estacionamiento=" + estacionamiento +
                ", mascota=" + mascota +
                ", moneda='" + moneda + '\'' +
                '}';
    }

    public int getCamas() {
        return camas;
    }

    public void setCamas(int camas) {
        this.camas = camas;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getBanos() {
        return banos;
    }

    public void setBanos(int banos) {
        this.banos = banos;
    }

    public boolean isEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(boolean estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public boolean isMascota() {
        return mascota;
    }

    public void setMascota(boolean mascota) {
        this.mascota = mascota;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public LugarClass() {
        nombreimagenes = new ArrayList<String>();
    }

    public List<String> getNombreimagenes() {
        return nombreimagenes;
    }

    public void setNombreimagenes(List<String> nombreimagenes) {
        this.nombreimagenes = nombreimagenes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
