package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.util.NetworkUtils;

import org.json.JSONObject;

public class SessionTask extends AsyncTaskLoader<JSONObject> {

    private String token;

    public SessionTask(@NonNull Context context, String token) {
        super(context);
        this.token = token;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {

        NetworkUtils networkUtils = new NetworkUtils();

        return networkUtils.getSession(token);
    }


}
