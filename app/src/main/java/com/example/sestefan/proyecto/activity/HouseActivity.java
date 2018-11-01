package com.example.sestefan.proyecto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.fragment.FacebookLoginFragment;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseAdapter;
import com.example.sestefan.proyecto.task.HouseTask;

public class HouseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Houses> {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Houses houses;
    private HouseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycledView);

        RecyclerViewClickListener adapterI = (view, position) -> {
            Response element = houses.getResponse().get(position);
            Intent wonderDetailActivityIntent = new Intent(this, HouseDetailActivity.class);
            wonderDetailActivityIntent.putExtra(HouseDetailActivity.EXTRA_DATA, element);
            startActivity(wonderDetailActivityIntent);
            adapter.notifyDataSetChanged();
        };
        adapter = new HouseAdapter(this, houses, adapterI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle queryBundle = new Bundle();
        getSupportLoaderManager().restartLoader(0, queryBundle, this);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FacebookLoginFragment()).commit();
        return true;
    }

    @NonNull
    @Override
    public Loader<Houses> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new HouseTask(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Houses> loader, Houses data) {
        Log.d("tasks", System.identityHashCode(this) + " OnPostExecute");
        adapter.setResponses(data);
        houses = data;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Houses> loader) {

    }
}
