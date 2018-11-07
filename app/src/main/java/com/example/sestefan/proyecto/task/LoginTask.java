package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.EmptyHouseRepository;
import com.example.sestefan.proyecto.domain.EmptyResponse;

public class LoginTask extends AsyncTaskLoader<EmptyResponse> {

    private String token;

    private String email;


    public LoginTask(@NonNull Context context, String token, String email) {
        super(context);
        this.token = token;
        this.email = email;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public EmptyResponse loadInBackground() {

        EmptyHouseRepository emptyHouseRepository = new EmptyHouseRepository();

        return emptyHouseRepository.logIn(token, email);
    }
}
