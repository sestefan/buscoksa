package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class HouseDTO implements Parcelable {

    private String TieneGarage;

    private String CantDormitorio;

    private String TienePatio;

    private String Precio;

    private String MaxResults;

    private String TieneBalcon;

    private String TieneParrillero;

    private String Barrio;

    public HouseDTO() {
    }

    protected HouseDTO(Parcel in) {
        TieneGarage = in.readString();
        CantDormitorio = in.readString();
        TienePatio = in.readString();
        Precio = in.readString();
        MaxResults = in.readString();
        TieneBalcon = in.readString();
        TieneParrillero = in.readString();
        Barrio = in.readString();
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TieneGarage);
        dest.writeString(CantDormitorio);
        dest.writeString(TienePatio);
        dest.writeString(Precio);
        dest.writeString(MaxResults);
        dest.writeString(TieneBalcon);
        dest.writeString(TieneParrillero);
        dest.writeString(Barrio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HouseDTO> CREATOR = new Parcelable.Creator<HouseDTO>() {
        @Override
        public HouseDTO createFromParcel(Parcel in) {
            return new HouseDTO(in);
        }

        @Override
        public HouseDTO[] newArray(int size) {
            return new HouseDTO[size];
        }
    };

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
