package com.example.sestefan.proyecto.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.FavoriteResponse;
import com.example.sestefan.proyecto.domain.Habitaciones;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseImagesAdapter;
import com.example.sestefan.proyecto.task.SaveFavoriteTask;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HouseDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<FavoriteResponse> {

    public static final String EXTRA_DATA = "extra_data";
    private static final String TOKEN = "token";

    private RecyclerView recyclerView;
    private HouseImagesAdapter adapter;

    private Response data;

    private String token;

    private TextView txtRoom;
    private TextView txtBathroom;
    private TextView txtDims;
    private TextView txtTitle;
    private TextView txtPrice;

    private TextView txtGarage;
    private TextView txtBarbecue;
    private TextView txtBalcony;
    private TextView txtGarden;

    MapView mapView;
    GoogleMap map;

    private FacebookLoginFragment.OnFragmentInteractionListener mListener;

    private Menu menu;

    public HouseDetailFragment() {
        // Required empty public constructor
    }

    public static HouseDetailFragment newInstance(Response data, String token) {
        HouseDetailFragment fragment = new HouseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DATA, data);
        args.putString(TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getParcelable(EXTRA_DATA);
            token = getArguments().getString(TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_house_detail, container, false);

        setHasOptionsMenu(true);

        txtRoom = v.findViewById(R.id.txt_room);
        txtBathroom = v.findViewById(R.id.txt_bath);
        txtDims = v.findViewById(R.id.txt_dims);
        txtTitle = v.findViewById(R.id.txt_title);
        txtPrice = v.findViewById(R.id.txt_detail_price);

        txtGarage = v.findViewById(R.id.txt_garage);
        txtBarbecue = v.findViewById(R.id.txt_barbecue);
        txtBalcony = v.findViewById(R.id.txt_balcony);
        txtGarden = v.findViewById(R.id.txt_garden);

        txtRoom.setText(String.format("%s %s", getContext().getString(R.string.detail_rooms), data.getInmuebleCantDormitorio()));
        txtBathroom.setText(String.format("%s %s", getContext().getString(R.string.detail_bathrooms), getBathQty()));
        txtDims.setText(String.format("%s %s", getContext().getString(R.string.detail_mts), data.getInmuebleMetrosCuadrados()));
        txtTitle.setText(String.format("%s %s", getContext().getString(R.string.detail_title), data.getInmuebleTitulo()));
        txtPrice.setText(String.format("%s %s", getContext().getString(R.string.detail_price), data.getInmueblePrecio()));

        boolean hasGarage = Boolean.valueOf(data.getInmuebleTieneGarage());
        boolean hasBarbecue = Boolean.valueOf(data.getInmuebleTieneParrillero());
        boolean hasBalcony = Boolean.valueOf(data.getInmuebleTieneBalcon());
        boolean hasGarden = Boolean.valueOf(data.getInmuebleTienePatio());
        txtGarage.setText(String.format("%s %s", getContext().getString(R.string.detail_garage), hasGarage ? getContext().getString(R.string.yes) : getContext().getString(R.string.no)));
        txtBarbecue.setText(String.format("%s %s", getContext().getString(R.string.detail_barbecue), hasBarbecue ? getContext().getString(R.string.yes) : getContext().getString(R.string.no)));
        txtBalcony.setText(String.format("%s %s", getContext().getString(R.string.detail_balcony), hasBalcony ? getContext().getString(R.string.yes) : getContext().getString(R.string.no)));
        txtGarden.setText(String.format("%s %s", getContext().getString(R.string.detail_garden), hasGarden ? getContext().getString(R.string.yes) : getContext().getString(R.string.no)));

        recyclerView = v.findViewById(R.id.recycledView2);

        RecyclerViewClickListener adapterI = (view, position) -> {
        };
        adapter = new HouseImagesAdapter(getContext(), data.getFotos(), adapterI);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        // Gets the MapView from the XML layout and creates it
        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(googleMap -> {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(false);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            map.setMyLocationEnabled(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(getContext());

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(-34.866944, -56.166667));
            map.animateCamera(cameraUpdate);
        });


        return v;
    }

    @Override
    public void onResume() {
//        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FacebookLoginFragment.OnFragmentInteractionListener) {
            mListener = (FacebookLoginFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        menu.clear();
        inflater.inflate(R.menu.favorite_menu, menu);
        MenuItem isntFavorite = menu.findItem(R.id.isnt_favorite);
        if (mListener.isFacebookLoggedIn()) {
            MenuItem isFavorite = menu.findItem(R.id.is_favorite);
            if (data.isFavorito()) {
                isFavorite.setVisible(true);
            } else {
                isntFavorite.setVisible(true);
            }
        } else {
            isntFavorite.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.is_favorite:
            case R.id.isnt_favorite:
                if (mListener.isFacebookLoggedIn()) {
                    Bundle queryBundle = new Bundle();
                    getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);

                    if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
                        getActivity().getSupportLoaderManager().initLoader(0, null, this);
                    }
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, FacebookLoginFragment.newInstance()).addToBackStack(null).commit();
                }
                break;
        }
        return true;
    }

    private String getBathQty() {
        ArrayList<Habitaciones> rooms = data.getHabitaciones();
        for (Habitaciones h : rooms) {
            if (h.getInmuebleHabitacionNombre().equalsIgnoreCase("baños") || h.getInmuebleHabitacionNombre().equalsIgnoreCase("baño")) {
                return h.getInmuebleHabitacionCantidad();
            }
        }
        return "0";
    }

    @NonNull
    @Override
    public Loader<FavoriteResponse> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new SaveFavoriteTask(getContext(), token, Integer.valueOf(data.getInmuebleId()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<FavoriteResponse> loader, FavoriteResponse data) {
        if (data.getResultado().equals("OK")) {
            if (menu.findItem(R.id.isnt_favorite).isVisible()) {
                menu.findItem(R.id.is_favorite).setVisible(true);
                menu.findItem(R.id.isnt_favorite).setVisible(false);
            } else {
                menu.findItem(R.id.is_favorite).setVisible(false);
                menu.findItem(R.id.isnt_favorite).setVisible(true);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<FavoriteResponse> loader) {

    }
}
