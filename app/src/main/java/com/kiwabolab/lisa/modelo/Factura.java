
package com.kiwabolab.lisa.modelo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Factura implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("establecimiento")
    @Expose
    private String establecimiento;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("img")
    @Expose
    private String img;
    private final static long serialVersionUID = 4060649064439095334L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
