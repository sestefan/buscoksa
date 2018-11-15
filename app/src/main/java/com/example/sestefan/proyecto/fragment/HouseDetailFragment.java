package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.FavoriteResponse;
import com.example.sestefan.proyecto.domain.Habitaciones;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseImagesAdapter;
import com.example.sestefan.proyecto.task.SaveFavoriteTask;

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

    private CheckBox chkGarage;
    private CheckBox chkBarbecue;
    private CheckBox chkBalcony;
    private CheckBox chkPlayground;

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

        chkGarage = v.findViewById(R.id.chk_garage);
        chkBarbecue = v.findViewById(R.id.chk_barbecue);
        chkBalcony = v.findViewById(R.id.chk_balcony);
        chkPlayground = v.findViewById(R.id.chk_playground);

        txtRoom.setText(data.getInmuebleCantDormitorio());
        txtBathroom.setText(getBathQty());
        txtDims.setText(data.getInmuebleMetrosCuadrados());
        txtTitle.setText(data.getInmuebleTitulo());

        chkGarage.setChecked(Boolean.valueOf(data.getInmuebleTieneGarage()));
        chkBarbecue.setChecked(Boolean.valueOf(data.getInmuebleTieneParrillero()));
        chkBalcony.setChecked(Boolean.valueOf(data.getInmuebleTieneBalcon()));
        chkPlayground.setChecked(Boolean.valueOf(data.getInmuebleTienePatio()));

        recyclerView = v.findViewById(R.id.recycledView2);

        RecyclerViewClickListener adapterI = (view, position) -> {
        };
        adapter = new HouseImagesAdapter(getContext(), data.getFotos(), adapterI);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        return v;
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
