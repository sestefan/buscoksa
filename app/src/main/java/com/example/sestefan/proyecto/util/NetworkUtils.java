package com.example.sestefan.proyecto.util;

import android.net.Uri;

import com.example.sestefan.proyecto.domain.Houses;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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

    private static final String LOGIN = "login";

    private static final String OBTENER_SESION = "obtenerSesion";

    private static final String BUSCAR_INMUEBLE = "buscarInmueble";

    private static final String LISTADO_FAVORITOS = "listadoFavoritos";

    private static final String GUARDAR_FAVORITO = "guardarFavorito";

    public JSONObject logIn(String token, String email) {

        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            BufferedReader reader = doPost(BASE_URL + LOGIN, body, token);
            Type gsonType = new TypeToken<JSONObject>() {
            }.getType();
            return new Gson().fromJson(reader, gsonType);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getSession(String token) {

        BufferedReader reader = null;
        try {
            reader = doPost(BASE_URL + OBTENER_SESION, new JSONObject(), token);
            Type gsonType = new TypeToken<JSONObject>() {
            }.getType();
            return new Gson().fromJson(reader, gsonType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Houses homeSearch() {
        JSONObject body = new JSONObject();
        BufferedReader reader = null;
        try {
            body.put("MaxResults", 10);
            body.put("Barrio", "");
            body.put("Precio", "");
            body.put("CantDormitorio", "");
            body.put("TieneParrillero", "");
            body.put("TieneGarage", "");
            body.put("TieneBalcon", "");
            body.put("TienePatio", "");
            reader = doPost(BASE_URL + BUSCAR_INMUEBLE, body, null);
            Type gsonType = new TypeToken<Houses>() {
            }.getType();
            return new Gson().fromJson(reader, gsonType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Houses bookmarkSearch(String token) {
        try {
            BufferedReader reader = doPost(BASE_URL + LISTADO_FAVORITOS, new JSONObject(), token);
            Type gsonType = new TypeToken<Houses>() {
            }.getType();
            return new Gson().fromJson(reader, gsonType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean bookmarkSave(String token, int houseId) {
        JSONObject body = new JSONObject();
        try {
            body.put("InmuebleId", houseId);
            doPost(BASE_URL + GUARDAR_FAVORITO, body, token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private BufferedReader doPost(String url, JSONObject body, String token) throws Exception {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            Uri builtURI = Uri.parse(url).buildUpon().build();
            URL requestUrl = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            if (token != null && !token.isEmpty()) {
                urlConnection.setRequestProperty("Authorization", token);
            }
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(body.toString().getBytes());

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

        } catch (Exception e) {
            try {
                throw e;
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JsonIOException e1) {
                e1.printStackTrace();
            } catch (JsonSyntaxException e1) {
                e1.printStackTrace();
            }
        }
        return reader;
    }

}
