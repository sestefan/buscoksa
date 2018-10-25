package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.util.NetworkUtils;
import com.example.sestefan.proyecto.domain.House;

import java.util.List;

public class HouseTask extends AsyncTaskLoader<List<House>> {

    public HouseTask(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<House> loadInBackground() {

        NetworkUtils networkUtils = new NetworkUtils();

        return networkUtils.getWorldWonders();
    }
}
