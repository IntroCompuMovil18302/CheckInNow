package Modelo;

import java.io.Serializable;

public class Reserva implements Serializable {
    private String lugarid;
    private String userid;
    private String fechaorigen;
    private String fechafin;

    public Reserva(String lugarid, String userid, String fechaorigen, String fechafin) {
        this.lugarid = lugarid;
        this.userid = userid;
        this.fechaorigen = fechaorigen;
        this.fechafin = fechafin;
    }

    public Reserva() {

    }

    @Override
    public String toString() {
        return "Reserva{" +
                "lugarid='" + lugarid + '\'' +
                ", userid='" + userid + '\'' +
                ", fechaorigen='" + fechaorigen + '\'' +
                ", fechafin='" + fechafin + '\'' +
                '}';
    }

    public String getLugarid() {
        return lugarid;
    }

    public void setLugarid(String lugarid) {
        this.lugarid = lugarid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFechaorigen() {
        return fechaorigen;
    }

    public void setFechaorigen(String fechaorigen) {
        this.fechaorigen = fechaorigen;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }
}
