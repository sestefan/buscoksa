package com.example.sestefan.proyecto.domain;

public class HouseDTO {
    private String TieneGarage;

    private String CantDormitorio;

    private String TienePatio;

    private String Precio;

    private String MaxResults;

    private String TieneBalcon;

    private String TieneParrillero;

    private String Barrio;

    public String getTieneGarage() {
        return TieneGarage;
    }

    public void setTieneGarage(String TieneGarage) {
        this.TieneGarage = TieneGarage;
    }

    public String getCantDormitorio() {
        return CantDormitorio;
    }

    public void setCantDormitorio(String CantDormitorio) {
        this.CantDormitorio = CantDormitorio;
    }

    public String getTienePatio() {
        return TienePatio;
    }

    public void setTienePatio(String TienePatio) {
        this.TienePatio = TienePatio;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String Precio) {
        this.Precio = Precio;
    }

    public String getMaxResults() {
        return MaxResults;
    }

    public void setMaxResults(String MaxResults) {
        this.MaxResults = MaxResults;
    }

    public String getTieneBalcon() {
        return TieneBalcon;
    }

    public void setTieneBalcon(String TieneBalcon) {
        this.TieneBalcon = TieneBalcon;
    }

    public String getTieneParrillero() {
        return TieneParrillero;
    }

    public void setTieneParrillero(String TieneParrillero) {
        this.TieneParrillero = TieneParrillero;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String Barrio) {
        this.Barrio = Barrio;
    }

    @Override
    public String toString() {
        return "ClassPojo [TieneGarage = " + TieneGarage + ", CantDormitorio = " + CantDormitorio + ", TienePatio = " + TienePatio + ", Precio = " + Precio + ", MaxResults = " + MaxResults + ", TieneBalcon = " + TieneBalcon + ", TieneParrillero = " + TieneParrillero + ", Barrio = " + Barrio + "]";
    }
}
