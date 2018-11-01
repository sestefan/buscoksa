package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Fotos implements Parcelable {

    private String InmuebleImagenUrl;

    public String getInmuebleImagenUrl() {
        return InmuebleImagenUrl;
    }

    public void setInmuebleImagenUrl(String InmuebleImagenUrl) {
        this.InmuebleImagenUrl = InmuebleImagenUrl;
    }


    protected Fotos(Parcel in) {
        InmuebleImagenUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(InmuebleImagenUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Fotos> CREATOR = new Parcelable.Creator<Fotos>() {
        @Override
        public Fotos createFromParcel(Parcel in) {
            return new Fotos(in);
        }

        @Override
        public Fotos[] newArray(int size) {
            return new Fotos[size];
        }
    };
}
