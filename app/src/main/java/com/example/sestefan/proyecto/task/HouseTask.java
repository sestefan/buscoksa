package com.example.sestefan.proyecto.task;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.sestefan.proyecto.api.HouseRepository;
import com.example.sestefan.proyecto.domain.HouseDTO;
import com.example.sestefan.proyecto.domain.Houses;

public class HouseTask extends AsyncTaskLoader<Houses> {

    private HouseDTO houseDTO;
    private String token;

    public HouseTask(@NonNull Context context, HouseDTO houseDTO, String token) {
        super(context);
        this.houseDTO = houseDTO;
        if (houseDTO == null) {
            this.houseDTO = new HouseDTO();
            this.houseDTO.setMaxResults("10");
        }
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

        return houseRepository.homeSearch(houseDTO, token);

    }
}
