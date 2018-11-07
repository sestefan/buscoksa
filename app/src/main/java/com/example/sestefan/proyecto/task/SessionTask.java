package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.UserHouseRepository;
import com.example.sestefan.proyecto.domain.User;

public class SessionTask extends AsyncTaskLoader<User> {

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
    public User loadInBackground() {

        UserHouseRepository userHouseRepository = new UserHouseRepository();

        return userHouseRepository.getSession(token);
    }


}
