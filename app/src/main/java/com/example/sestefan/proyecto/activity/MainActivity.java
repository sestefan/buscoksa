package com.example.sestefan.proyecto.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.fragment.FacebookLoginFragment;
import com.example.sestefan.proyecto.fragment.FragmentEventListener;
import com.example.sestefan.proyecto.fragment.HomePageFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentEventListener {

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
    }

    @Override
    public void showLoginMenuItem() {
        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_bookmarks).setVisible(false);
    }

    @Override
    public void showPostLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance()).commit();
        navigationView.getMenu().findItem(R.id.nav_login).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        ImageView imgFacebookLogin = navigationView.getHeaderView(0).findViewById(R.id.img_fb_profile);
        String facebookUserId = AccessToken.getCurrentAccessToken().getUserId();
        String facebookLoginImageUrl = "http://graph.facebook.com/" + facebookUserId + "/picture?type=large";
        String facebookFullName = Profile.getCurrentProfile().getName();
        TextView txtFacebookFullName = navigationView.getHeaderView(0).findViewById(R.id.fb_full_name);
        if (facebookLoginImageUrl != null && !facebookLoginImageUrl.isEmpty()) {
            Picasso.get().load(facebookLoginImageUrl).into(imgFacebookLogin);
        } else {
            Picasso.get().load(R.drawable.menu_header_img).into(imgFacebookLogin);
        }
        txtFacebookFullName.setText(!facebookFullName.isEmpty() ? facebookFullName : "You Know Who");

        GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                Log.d("TEST", user.optString("email"));
                Log.d("TEST", user.optString("name"));
                Log.d("TEST", user.optString("id"));
            }
        }).executeAsync();
    }

    private boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();

    }
}
