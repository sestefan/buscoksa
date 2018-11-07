package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HousesRepository;
import com.example.sestefan.proyecto.domain.Houses;

public class BookmarkTask extends AsyncTaskLoader<Houses> {

    private String token;

    public BookmarkTask(@NonNull Context context, String token) {
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

        HousesRepository housesRepository = new HousesRepository();

        return housesRepository.bookmarkSearch(token);
    }
}
