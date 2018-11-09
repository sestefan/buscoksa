package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HouseRepository;
import com.example.sestefan.proyecto.domain.User;

public class SessionTask extends AsyncTaskLoader<User> {

    private String token;
    private String email;

    public SessionTask(@NonNull Context context, String token, String email) {
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
    public User loadInBackground() {

        HouseRepository houseRepository = new HouseRepository();

        User session = houseRepository.getSession(token);

        if (session.getUsuarioNombre().isEmpty()) {
            houseRepository.logIn(token, email);
            session.setUsuarioNombre(email);
        }

        return session;
    }


}
