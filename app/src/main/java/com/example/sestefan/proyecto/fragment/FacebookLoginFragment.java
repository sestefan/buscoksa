package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sestefan.proyecto.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class FacebookLoginFragment extends Fragment {

    private static final String EMAIL = "email";

    private OnFragmentInteractionListener onFragmentInteractionListener;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;


    public FacebookLoginFragment() {
        // Required empty public constructor
    }

    public static FacebookLoginFragment newInstance() {
        FacebookLoginFragment fragment = new FacebookLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    onFragmentInteractionListener.showLoginMenuItem();
                }
            }
        };
        accessTokenTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_facebook_login, container, false);
        loginButton = v.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onFragmentInteractionListener.hideLoginMenuItem();
                onFragmentInteractionListener.showPostLoginFragment();
            }

            @Override
            public void onCancel() {
                onFragmentInteractionListener.showLoginMenuItem();

            }

            @Override
            public void onError(FacebookException error) {
                onFragmentInteractionListener.showLoginMenuItem();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        accessTokenTracker.stopTracking();
        super.onDestroy();
    }

    public static interface OnFragmentInteractionListener {

        void hideLoginMenuItem();

        void showLoginMenuItem();

        void showPostLoginFragment();

        void performLogin(String sessionId);

    }
}
