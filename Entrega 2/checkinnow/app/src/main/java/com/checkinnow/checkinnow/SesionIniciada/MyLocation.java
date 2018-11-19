package com.checkinnow.checkinnow.SesionIniciada;

class MyLocation {

    double latitude;
    double longitude;
    String name;
    String fechaDuracion;
    int precionoche;

    public MyLocation() {
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", fechaDuracion='" + fechaDuracion + '\'' +
                ", precionoche=" + precionoche +
                '}';
    }
    public String toStringLugar() {
        return "[" +
                " Lugar :" + name +
                ", precionoche=" + precionoche +
                ']';
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFechaDuracion() {
        return fechaDuracion;
    }

    public void setFechaDuracion(String fechaDuracion) {
        this.fechaDuracion = fechaDuracion;
    }

    public int getPrecionoche() {
        return precionoche;
    }

    public void setPrecionoche(int precionoche) {
        this.precionoche = precionoche;
    }
}
