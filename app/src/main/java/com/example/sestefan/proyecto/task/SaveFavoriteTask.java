package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HouseRepository;
import com.example.sestefan.proyecto.domain.FavoriteResponse;

public class SaveFavoriteTask extends AsyncTaskLoader<FavoriteResponse> {

    private String token;

    private int houseId;

    public SaveFavoriteTask(@NonNull Context context, String token, int houseId) {
        super(context);
        this.token = token;
        this.houseId = houseId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public FavoriteResponse loadInBackground() {

        HouseRepository houseRepository = new HouseRepository();

        return houseRepository.bookmarkSave(token, houseId);
    }
}
