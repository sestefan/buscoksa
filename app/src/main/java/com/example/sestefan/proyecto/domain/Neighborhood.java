package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Neighborhood implements Parcelable {

    private String name;

    private double latitude;

    private double longitude;

    protected Neighborhood(Parcel in) {
        name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Neighborhood> CREATOR = new Parcelable.Creator<Neighborhood>() {
        @Override
        public Neighborhood createFromParcel(Parcel in) {
            return new Neighborhood(in);
        }

        @Override
        public Neighborhood[] newArray(int size) {
            return new Neighborhood[size];
        }
    };
}
