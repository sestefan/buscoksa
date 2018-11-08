package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.domain.EmptyResponse;

public class SaveBookmarkTask extends AsyncTaskLoader<EmptyResponse> {

    private String token;

    private int houseId;

    public SaveBookmarkTask(@NonNull Context context, String token, int houseId) {
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
    public EmptyResponse loadInBackground() {

        return null;
    }
}
