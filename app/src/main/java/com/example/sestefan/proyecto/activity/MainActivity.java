package com.example.sestefan.proyecto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.fragment.FacebookLoginFragment;
import com.example.sestefan.proyecto.fragment.FavoriteFragment;
import com.example.sestefan.proyecto.fragment.HelpFragment;
import com.example.sestefan.proyecto.fragment.HomePageFragment;
import com.example.sestefan.proyecto.fragment.TermsAndCondsFragment;
import com.example.sestefan.proyecto.util.facebook.FacebookLoginHelper;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FacebookLoginFragment.OnFragmentInteractionListener {

    private FacebookLoginHelper.FacebookLoginHelperDto facebookLoginHelperDto = null;

    ImageView imgFacebookLogin;

    TextView txtFacebookFullName;

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
            FacebookLoginHelper.getFacebookInfo(AccessToken.getCurrentAccessToken(), (id, name, email, imageUrl) -> {
                facebookLoginHelperDto = new FacebookLoginHelper.FacebookLoginHelperDto(id, name, email, imageUrl);
                postFacebookLogin(facebookLoginHelperDto);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance(isFacebookLoggedIn(), facebookLoginHelperDto.getId())).commit();
            });
        } else {
            if (navigationView.getMenu().findItem(R.id.nav_favorite).isVisible()) {
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance(isFacebookLoggedIn(), facebookLoginHelperDto.getId())).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, HomePageFragment.newInstance(isFacebookLoggedIn(), facebookLoginHelperDto != null ? facebookLoginHelperDto.getId() : null)).commit();
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, FacebookLoginFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, FacebookLoginFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.nav_favorite:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, FavoriteFragment.newInstance(facebookLoginHelperDto.getId())).addToBackStack(null).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, HelpFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.nav_terms_conds:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, TermsAndCondsFragment.newInstance()).addToBackStack(null).commit();
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
    public boolean isFacebookLoggedIn() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();

    }

    @Override
    public void postFacebookLogin(FacebookLoginHelper.FacebookLoginHelperDto facebookLoginHelperDto) {

        this.facebookLoginHelperDto = facebookLoginHelperDto;

        navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_login).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        imgFacebookLogin = navigationView.getHeaderView(0).findViewById(R.id.img_fb_profile);
        txtFacebookFullName = navigationView.getHeaderView(0).findViewById(R.id.fb_full_name);

        String facebookLoginImageUrl = facebookLoginHelperDto.getImageUrl();
        if (facebookLoginImageUrl != null && !facebookLoginImageUrl.isEmpty()) {
            Picasso.get().load(facebookLoginImageUrl).transform(new CropCircleTransformation()).into(imgFacebookLogin);
        } else {
            Picasso.get().load(R.drawable.menu_header_img).transform(new CropCircleTransformation()).into(imgFacebookLogin);
        }
        txtFacebookFullName.setText(String.format("%s %s", getString(R.string.welcome), facebookLoginHelperDto.getName()));
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void postFacebookLogOut() {

        navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
        facebookLoginHelperDto = null;
        txtFacebookFullName.setText("");
        imgFacebookLogin = navigationView.getHeaderView(0).findViewById(R.id.img_fb_profile);
        Picasso.get().load(R.drawable.menu_header_img).transform(new CropCircleTransformation()).into(imgFacebookLogin);

    }

}
