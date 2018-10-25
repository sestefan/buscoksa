package com.example.sestefan.proyecto.util;

import android.net.Uri;

import com.example.sestefan.proyecto.domain.House;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.myjson.com/bins/j9h5c";

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
//    private String numberJSONString = null;


    public List<House> getWorldWonders() {

        LinkedList<House> result = null;

        try {
            Uri builtURI = Uri.parse(BASE_URL).buildUpon().build();
            URL requestUrl = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            Type gsonType = new TypeToken<LinkedList<House>>() {
            }.getType();
            result = new Gson().fromJson(reader, gsonType);

//            StringBuilder builder = new StringBuilder();
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//                builder.append("\n");
//            }
//
//            if (builder.length() == 0) {
//                return null;
//            }
//
//            numberJSONString = builder.toString();
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
