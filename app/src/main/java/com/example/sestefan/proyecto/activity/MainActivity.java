package com.example.sestefan.proyecto.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.fragment.BookmarkFragment;
import com.example.sestefan.proyecto.fragment.FacebookLoginFragment;
import com.example.sestefan.proyecto.fragment.HelpFragment;
import com.example.sestefan.proyecto.fragment.HomePageFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FacebookLoginFragment.OnFragmentInteractionListener, HomePageFragment.OnFragmentInteractionListener, HelpFragment.OnFragmentInteractionListener,
        BookmarkFragment.OnFragmentInteractionListener {

    private static final String FACEBOOK_GRAPH_URL = "http://graph.facebook.com/{__USER_ID__}/picture?type=large";

    private String facebookSessionId;
    private String facebookEmail;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (isFacebookLoggedIn()) {
            hideLoginMenuItem();
            showPostLoginFragment();
        } else {
            if (navigationView.getMenu().findItem(R.id.nav_bookmarks).isVisible()) {
                navigationView.getMenu().findItem(R.id.nav_bookmarks).setVisible(false);
            }
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, HomePageFragment.newInstance()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance()).commit();
                break;
            case R.id.nav_login:
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FacebookLoginFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_bookmarks:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BookmarkFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HelpFragment()).addToBackStack(null).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void hideLoginMenuItem() {
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_bookmarks).setVisible(true);
    }

    @Override
    public void showLoginMenuItem() {
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_bookmarks).setVisible(false);
    }

    @Override
    public void showPostLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance()).commit();
        navigationView.getMenu().findItem(R.id.nav_login).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        ImageView imgFacebookLogin = navigationView.getHeaderView(0).findViewById(R.id.img_fb_profile);
        TextView txtFacebookFullName = navigationView.getHeaderView(0).findViewById(R.id.fb_full_name);
        // App code
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            facebookSessionId = object.getString("id");
                            String facebookLoginImageUrl = FACEBOOK_GRAPH_URL.replace("{__USER_ID__}", facebookSessionId);
                            if (facebookLoginImageUrl != null && !facebookLoginImageUrl.isEmpty()) {
                                Picasso.get().load(facebookLoginImageUrl).transform(new CropCircleTransformation()).into(imgFacebookLogin);
                            } else {
                                Picasso.get().load(R.drawable.menu_header_img).transform(new CropCircleTransformation()).into(imgFacebookLogin);
                            }
                            txtFacebookFullName.setText(!object.getString("name").isEmpty() ? object.getString("name") : "You Know Who");

                            // Application code
                            facebookEmail = object.getString("email");
                        } catch (Exception e) {
                            return;
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();

    }
}
