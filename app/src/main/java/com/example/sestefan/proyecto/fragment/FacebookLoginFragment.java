package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.User;
import com.example.sestefan.proyecto.task.SessionTask;
import com.example.sestefan.proyecto.util.facebook.FacebookLoginHelper;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Objects;

public class FacebookLoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<User> {

    private static final String EMAIL = "email";
    private static final String NEIGHBORHOODS = "neighborhoods";

    private FacebookLoginHelper.FacebookLoginHelperDto facebookLoginHelperDto;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;

    private Bundle neighborhoods;


    public FacebookLoginFragment() {
        // Required empty public constructor
    }

    public static FacebookLoginFragment newInstance(Bundle neighborhoods) {
        FacebookLoginFragment fragment = new FacebookLoginFragment();
        Bundle args = new Bundle();
        args.putBundle(NEIGHBORHOODS, neighborhoods);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            neighborhoods = getArguments().getBundle(NEIGHBORHOODS);
        }
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                    for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                        fragmentManager.popBackStack();
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance(false, neighborhoods, null)).commit();
                    onFragmentInteractionListener.postFacebookLogOut();
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
                FacebookLoginHelper.getFacebookInfo(AccessToken.getCurrentAccessToken(), (id, name, email, imageUrl) -> {
                    facebookLoginHelperDto = new FacebookLoginHelper.FacebookLoginHelperDto(id, name, email, imageUrl);
                    Bundle queryBundle = new Bundle();
                    getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, FacebookLoginFragment.this);

                    if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
                        getActivity().getSupportLoaderManager().initLoader(0, null, FacebookLoginFragment.this);
                    }
                    onFragmentInteractionListener.postFacebookLogin(facebookLoginHelperDto);
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
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

    @NonNull
    @Override
    public Loader<User> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new SessionTask(this.getContext(), facebookLoginHelperDto.getId(), facebookLoginHelperDto.getEmail());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<User> loader, User user) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<User> loader) {

    }

    public interface OnFragmentInteractionListener {

        boolean isFacebookLoggedIn();

        void postFacebookLogin(FacebookLoginHelper.FacebookLoginHelperDto facebookLoginHelperDto);

        void postFacebookLogOut();

    }

}
