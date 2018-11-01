package com.example.sestefan.proyecto.util;

import android.net.Uri;

import com.example.sestefan.proyecto.domain.Houses;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String BASE_URL = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/";

    private static final String BUSCAR_INMUEBLE = "buscarInmueble";

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;

    public Houses homeSearch() {
        JSONObject body = new JSONObject();
        try {
            body.put("MaxResults", 10);
            body.put("Barrio", "");
            body.put("Precio", "");
            body.put("CantDormitorio", "");
            body.put("TieneParrillero", "");
            body.put("TieneGarage", "");
            body.put("TieneBalcon", "");
            body.put("TienePatio", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return doPost(BASE_URL + BUSCAR_INMUEBLE, body);
    }

    private Houses doPost(String url, JSONObject body) {

        Houses result = null;

        try {
            Uri builtURI = Uri.parse(url).buildUpon().build();
            URL requestUrl = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(body.toString().getBytes());

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            Type gsonType = new TypeToken<Houses>() {
            }.getType();
            result = new Gson().fromJson(reader, gsonType);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
