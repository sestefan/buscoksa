package com.example.sestefan.proyecto.domain;

import org.json.JSONObject;

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

    public JSONObject toJson() {
        JSONObject body = new JSONObject();
        try {
            body.put("MaxResults", getMaxResults() == null ? "" : getMaxResults());
            body.put("Barrio", getBarrio() == null ? "" : getBarrio());
            body.put("Precio", getPrecio() == null ? "" : getPrecio());
            body.put("CantDormitorio", getCantDormitorio() == null ? "" : getCantDormitorio());
            body.put("TieneParrillero", getTieneParrillero() == null ? "" : getTieneParrillero());
            body.put("TieneGarage", getTieneGarage() == null ? "" : getTieneGarage());
            body.put("TieneBalcon", getTieneBalcon() == null ? "" : getTieneBalcon());
            body.put("TienePatio", getTienePatio() == null ? "" : getTienePatio());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return body;
    }
}
