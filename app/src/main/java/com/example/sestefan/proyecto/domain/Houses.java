package com.example.sestefan.proyecto.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Houses implements Parcelable {

    private ArrayList<Response> Response;

    public ArrayList<Response> getResponse() {
        return Response;
    }

    public void setResponse(ArrayList<Response> Response) {
        this.Response = Response;
    }

    protected Houses(Parcel in) {
        if (in.readByte() == 0x01) {
            Response = new ArrayList<Response>();
            in.readList(Response, Response.class.getClassLoader());
        } else {
            Response = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Response == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Response);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Houses> CREATOR = new Parcelable.Creator<Houses>() {
        @Override
        public Houses createFromParcel(Parcel in) {
            return new Houses(in);
        }

        @Override
        public Houses[] newArray(int size) {
            return new Houses[size];
        }
    };
}