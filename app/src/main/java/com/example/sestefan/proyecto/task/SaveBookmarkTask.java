package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.util.NetworkUtils;

public class SaveBookmarkTask extends AsyncTaskLoader<Boolean> {

    public SaveBookmarkTask(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {

        NetworkUtils networkUtils = new NetworkUtils();

        return networkUtils.bookmarkSave("", 1);
    }
}
