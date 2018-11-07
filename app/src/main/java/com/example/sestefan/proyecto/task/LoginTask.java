package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.util.NetworkUtils;

public class LoginTask extends AsyncTaskLoader<Houses> {

    public LoginTask(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Houses loadInBackground() {

        NetworkUtils networkUtils = new NetworkUtils();

        return networkUtils.homeSearch();
    }
}
