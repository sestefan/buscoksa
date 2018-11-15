package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HouseRepository;
import com.example.sestefan.proyecto.domain.Houses;

public class FavoriteTask extends AsyncTaskLoader<Houses> {

    private String token;

    public FavoriteTask(@NonNull Context context, String token) {
        super(context);
        this.token = token;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Houses loadInBackground() {

        HouseRepository houseRepository = new HouseRepository();
        return houseRepository.bookmarkSearch(token);
    }
}
