package com.checkinnow.checkinnow.Clases;

public class Usuario {

    String name;
    String apelligo;
    String contraseña;
    String pais;
    String fecha;
    String utlImg;

    public Usuario() {
    }

    public Usuario(String name, String apelligo, String contraseña, String pais, String fecha, String utlImg) {
        this.name = name;
        this.apelligo = apelligo;
        this.contraseña = contraseña;
        this.pais = pais;
        this.fecha = fecha;
        this.utlImg = utlImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApelligo() {
        return apelligo;
    }

    public void setApelligo(String apelligo) {
        this.apelligo = apelligo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUtlImg() {
        return utlImg;
    }

    public void setUtlImg(String utlImg) {
        this.utlImg = utlImg;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", apelligo='" + apelligo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", pais='" + pais + '\'' +
                ", fecha='" + fecha + '\'' +
                ", utlImg='" + utlImg + '\'' +
                '}';
    }
}
