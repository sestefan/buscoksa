package com.example.sestefan.proyecto.util.facebook;


import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

public class FacebookLoginHelper {

    private static final String FACEBOOK_GRAPH_URL = "http://graph.facebook.com/{__USER_ID__}/picture?type=large";

    private static String id;

    private static String name;

    private static String email;

    private static String imageUrl;

    public static void getFacebookInfo(AccessToken accessToken, FacebookLoginHelperCallback callback) {

        id = accessToken.toString();

        GraphRequest request = GraphRequest.newMeRequest(accessToken, (object, response) -> {

            try {
                name = !object.getString("name").isEmpty() ? object.getString("name") : "You Know Who";
                email = object.getString("email");
                imageUrl = FACEBOOK_GRAPH_URL.replace("{__USER_ID__}", object.getString("id"));
                callback.getInfo(id, name, email, imageUrl);
            } catch (Exception e) {
                return;
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static class FacebookLoginHelperDto {

        private String id;

        private String name;

        private String email;

        private String imageUrl;

        public FacebookLoginHelperDto(String id, String name, String email, String imageUrl) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.imageUrl = imageUrl;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public interface FacebookLoginHelperCallback {

        void getInfo(String id, String name, String email, String imageUrl);

    }

}
