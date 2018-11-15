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
import android.view.View;
import android.view.ViewGroup;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseAdapter;
import com.example.sestefan.proyecto.task.FavoriteTask;

//TODO: Hacer Heredar la clase de HomeFragment y cambiar las propiedades del padre que correspondan a protected
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Houses> {

    private static final String TOKEN = "token";

    private RecyclerView recyclerView;
    private Houses houses;
    private HouseAdapter adapter;

    private String token;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String token) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN, token);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString("token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = v.findViewById(R.id.recycledView);

        RecyclerViewClickListener adapterI = (view, position) -> {
            Response element = houses.getResponse().get(position);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HouseDetailFragment.newInstance(element, token)).addToBackStack(null).commit();
            adapter.notifyDataSetChanged();
        };
        adapter = new HouseAdapter(getContext(), houses, true, adapterI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle queryBundle = new Bundle();
        getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);

        if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
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
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @NonNull
    @Override
    public Loader<Houses> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new FavoriteTask(getContext(), token);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Houses> loader, Houses houses) {
        adapter.setResponses(houses);
        this.houses = houses;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Houses> loader) {

    }

    public interface OnFragmentInteractionListener {
    }
}
