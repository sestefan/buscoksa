package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Habitaciones implements Parcelable {

    private String InmuebleHabitacionNombre;

    private String InmuebleHabitacionCantidad;

    public String getInmuebleHabitacionNombre() {
        return InmuebleHabitacionNombre;
    }

    public void setInmuebleHabitacionNombre(String InmuebleHabitacionNombre) {
        this.InmuebleHabitacionNombre = InmuebleHabitacionNombre;
    }

    public String getInmuebleHabitacionCantidad() {
        return InmuebleHabitacionCantidad;
    }

    public void setInmuebleHabitacionCantidad(String InmuebleHabitacionCantidad) {
        this.InmuebleHabitacionCantidad = InmuebleHabitacionCantidad;
    }

    protected Habitaciones(Parcel in) {
        InmuebleHabitacionNombre = in.readString();
        InmuebleHabitacionCantidad = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(InmuebleHabitacionNombre);
        dest.writeString(InmuebleHabitacionCantidad);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Habitaciones> CREATOR = new Parcelable.Creator<Habitaciones>() {
        @Override
        public Habitaciones createFromParcel(Parcel in) {
            return new Habitaciones(in);
        }

        @Override
        public Habitaciones[] newArray(int size) {
            return new Habitaciones[size];
        }
    };
}
