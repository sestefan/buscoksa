package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.HouseDTO;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseAdapter;
import com.example.sestefan.proyecto.task.HouseTask;

public class HomePageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Houses> {

    private static final String ARG_USER_LOGGED_IN = "arg_user_logged_in";
    private static final String ARG_TOKEN = "token";

    private RecyclerView recyclerView;
    private Houses houses;
    private HouseAdapter adapter;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    private boolean isUserLoggedIn;

    private String token;

    private HouseDTO houseDTO = null;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(boolean isUserLoggedIn, String token) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_USER_LOGGED_IN, isUserLoggedIn);
        args.putString(ARG_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUserLoggedIn = getArguments().getBoolean(ARG_USER_LOGGED_IN);
            token = getArguments().getString(ARG_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);

        setHasOptionsMenu(true);

        recyclerView = v.findViewById(R.id.recycledView);

        RecyclerViewClickListener adapterI = (view, position) -> {
            Response element = houses.getResponse().get(position);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HouseDetailFragment.newInstance(element, token)).addToBackStack(null).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.land_detail, HouseDetailFragment.newInstance(element, token)).addToBackStack(null).commit();
            }
            adapter.notifyDataSetChanged();
        };
        adapter = new HouseAdapter(getContext(), houses, isUserLoggedIn, adapterI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle queryBundle = new Bundle();
        getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);

        if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }

        return v;
    }

    @NonNull
    @Override
    public Loader<Houses> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new HouseTask(getContext(), houseDTO, token);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Houses> loader, Houses data) {
        adapter.setResponses(data);
        houses = data;
        adapter.notifyDataSetChanged();
        houseDTO = null;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Houses> loader) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomePageFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (HomePageFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                houseDTO = new HouseDTO();
                houseDTO.setBarrio(s.toLowerCase());
                houseDTO.setMaxResults("100");

                getActivity().getSupportLoaderManager().restartLoader(0, null, HomePageFragment.this);

                if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
                    getActivity().getSupportLoaderManager().initLoader(0, null, HomePageFragment.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(getContext(), "Filtrooo", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public static interface OnFragmentInteractionListener {

    }
}
