package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HousesRepository;
import com.example.sestefan.proyecto.domain.Houses;

public class HouseTask extends AsyncTaskLoader<Houses> {

    public HouseTask(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Houses loadInBackground() {

        HousesRepository housesRepository = new HousesRepository();

        Houses house = housesRepository.homeSearch();

        return house;
    }
}
