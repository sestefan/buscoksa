package com.example.sestefan.proyecto.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup;

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
    private static final String NEIGHBORHOODS = "neighborhoods";

    private RecyclerView recyclerView;
    private Houses houses;
    private HouseAdapter adapter;

    private boolean isUserLoggedIn;
    private boolean linearLayoutNormal;

    private String token;

    private Bundle neighborhoods;

    private HouseDTO houseDTO = null;

    private HouseDTO filter = null;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(boolean isUserLoggedIn, Bundle neighborhoods, String token) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_USER_LOGGED_IN, isUserLoggedIn);
        args.putString(ARG_TOKEN, token);
        args.putBundle(NEIGHBORHOODS, neighborhoods);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUserLoggedIn = getArguments().getBoolean(ARG_USER_LOGGED_IN);
            token = getArguments().getString(ARG_TOKEN);
            neighborhoods = getArguments().getBundle(NEIGHBORHOODS);
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HouseDetailFragment.newInstance(element, neighborhoods, token)).addToBackStack(null).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.land_detail, HouseDetailFragment.newInstance(element, neighborhoods, token)).addToBackStack(null).commit();
            }
            adapter.notifyDataSetChanged();
        };
        adapter = new HouseAdapter(getContext(), houses, isUserLoggedIn, true, adapterI);
        linearLayoutNormal = false;
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
        if (houses.getResponse().size() == 0) {
            filter = null;
        }
        houseDTO = null;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Houses> loader) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                houseDTO = new HouseDTO();
                houseDTO.setBarrio(s.toLowerCase());
                houseDTO.setMaxResults("100");
                filter = null;

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

        menu.findItem(R.id.search).setOnActionExpandListener(new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                getActivity().getSupportLoaderManager().restartLoader(0, null, HomePageFragment.this);

                if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
                    getActivity().getSupportLoaderManager().initLoader(0, null, HomePageFragment.this);
                }
                return true;
            }
        });

        MenuItem filterMenu = menu.findItem(R.id.filter);
        filterMenu.setOnMenuItemClickListener(menuItem -> {
            DialogFragment dialog = FilterDialogFragment.newInstance(houses, filter);
            dialog.setTargetFragment(this, 1);
            dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialogfragment));
            return true;
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MenuItem layoutMenu = menu.findItem(R.id.layoutView);
            layoutMenu.setVisible(true);
            layoutMenu.setOnMenuItemClickListener(menuItem -> {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    menuItem.setIcon(R.drawable.baseline_horizontal_split_24);
                    adapter.setLayoutNormal(linearLayoutNormal);
                    recyclerView.setAdapter(adapter);
                    recyclerView.requestLayout();
                    linearLayoutNormal = true;
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    if (linearLayoutNormal) {
                        menuItem.setIcon(R.drawable.baseline_dashboard_24);
                        adapter.setLayoutNormal(linearLayoutNormal);
                        recyclerView.setAdapter(adapter);
                        recyclerView.requestLayout();
                        linearLayoutNormal = false;
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        menuItem.setIcon(R.drawable.baseline_list_24);
                        linearLayoutNormal = false;
                    }
                }
                return true;
            });
        } else {
            MenuItem layoutMenu = menu.findItem(R.id.layoutView);
            layoutMenu.setVisible(false);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        Bundle extras = data.getExtras();

        if (extras == null) {
            return;
        }

        HouseDTO houseDTO = extras.getParcelable("dataFilter");
        this.houseDTO = houseDTO;
        this.filter = houseDTO;

        getActivity().getSupportLoaderManager().restartLoader(0, null, HomePageFragment.this);

        if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
            getActivity().getSupportLoaderManager().initLoader(0, null, HomePageFragment.this);
        }
    }

}
