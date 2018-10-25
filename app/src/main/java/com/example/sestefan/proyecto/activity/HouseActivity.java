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
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.domain.House;
import com.example.sestefan.proyecto.task.HouseTask;
import com.example.sestefan.proyecto.recycler_view.adapter.ListHouseAdapter;

import java.util.LinkedList;
import java.util.List;

public class HouseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<House>> {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinkedList<House> newList;
    private ListHouseAdapter adapter;

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
            House element = newList.get(position);
            Intent wonderDetailActivityIntent = new Intent(this, HouseDetailActivity.class);
            wonderDetailActivityIntent.putExtra(HouseDetailActivity.EXTRA_DATA, element);
            startActivity(wonderDetailActivityIntent);
            adapter.notifyDataSetChanged();
        };
        adapter = new ListHouseAdapter(this, newList, adapterI);
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
        return false;
    }

    @NonNull
    @Override
    public Loader<List<House>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new HouseTask(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<House>> loader, List<House> data) {
        Log.d("tasks", System.identityHashCode(this) + " OnPostExecute");
        adapter.setHouses((LinkedList<House>) data);
        newList = (LinkedList<House>) data;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<House>> loader) {

    }
}
