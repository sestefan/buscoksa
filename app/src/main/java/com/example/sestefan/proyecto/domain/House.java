package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class House implements Parcelable {

    private int id;
    private String nombre;
    private String pais;
    private String epoca;
    private String extraInfo;
    private ArrayList<String> imagenes;

    protected House(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        pais = in.readString();
        epoca = in.readString();
        extraInfo = in.readString();
        if (in.readByte() == 0x01) {
            imagenes = new ArrayList<String>();
            in.readList(imagenes, String.class.getClassLoader());
        } else {
            imagenes = null;
        }
    }

    public static final Creator<House> CREATOR = new Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getEpoca() {
        return epoca;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(pais);
        dest.writeString(epoca);
        dest.writeString(extraInfo);
        if (imagenes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imagenes);
        }
    }
}
