package com.example.sestefan.proyecto.api;

import android.net.Uri;

import com.example.sestefan.proyecto.domain.EmptyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HouseRepository {

    private static final String BASE_URL = "http://173.233.86.183:8080/CursoAndroidWebApp/rest/";

    private static final String LOGIN = "login";

    private static final String OBTENER_SESION = "obtenerSesion";

    private static final String BUSCAR_INMUEBLE = "buscarInmueble";

    private static final String LISTADO_FAVORITOS = "listadoFavoritos";

    private static final String GUARDAR_FAVORITO = "guardarFavorito";

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;

    public EmptyResponse logIn(String token, String email) {
        JSONObject body = null;
        try {
            body = new JSONObject().put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject result = doPost(BASE_URL + LOGIN, body, token);
        ObjectMapper objectMapper = new ObjectMapper();
        EmptyResponse response = objectMapper.readValue(result.getString().getBytes(), EmptyResponse.class);
        return response;
    }

    public ResponseType getSession(String token) {
        return doPost(BASE_URL + OBTENER_SESION, new JSONObject(), token);
    }

    public ResponseType homeSearch() {

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
            return doPost(BASE_URL + BUSCAR_INMUEBLE, body, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ResponseType bookmarkSearch(String token) {
        return doPost(BASE_URL + LISTADO_FAVORITOS, new JSONObject(), token);
    }

    public ResponseType bookmarkSave(String token, int houseId) {
        JSONObject body = new JSONObject();
        try {
            body.put("InmuebleId", houseId);
            return doPost(BASE_URL + GUARDAR_FAVORITO, body, token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject doPost(String url, JSONObject body, String token) {

        JSONObject result;

        try {

            Uri builtURI = Uri.parse(url).buildUpon().build();
            URL requestUrl = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            if (token != null && !token.isEmpty()) {
                urlConnection.setRequestProperty("Authorization", token);
            }
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(body.toString().getBytes());

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = new JSONObject(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return result;
    }

}
