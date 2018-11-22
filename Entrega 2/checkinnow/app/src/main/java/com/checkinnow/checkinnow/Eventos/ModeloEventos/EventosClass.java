package com.checkinnow.checkinnow.Eventos.ModeloEventos;

import java.util.ArrayList;
import java.util.List;

public class EventosClass {

    private double latitude;
    private double longitud;
    private String descripcion;
    private String nombre;
    private String tipo;
    private String ID;
    private String path;
    private String fechaDesde;
    private String fechaHasta;
    private List<String> nombreimagenes;

    public EventosClass() {
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

    @Override
    public String toString() {
        return "EventosClass{" +
                "latitude=" + latitude +
                ", longitud=" + longitud +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ID='" + ID + '\'' +
                ", path='" + path + '\'' +
                ", fechaDesde='" + fechaDesde + '\'' +
                ", fechaHasta='" + fechaHasta + '\'' +
                ", nombreimagenes=" + nombreimagenes +
                '}';
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String valor) {
        this.descripcion = valor;
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
