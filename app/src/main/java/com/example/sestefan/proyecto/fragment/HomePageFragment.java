package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.sestefan.proyecto.activity.HouseDetailActivity;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseAdapter;
import com.example.sestefan.proyecto.task.HouseTask;

public class HomePageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Houses> {

    private RecyclerView recyclerView;
    private Houses houses;
    private HouseAdapter adapter;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(/*set arguments*/) {
        HomePageFragment fragment = new HomePageFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);

        recyclerView = v.findViewById(R.id.recycledView);

        RecyclerViewClickListener adapterI = (view, position) -> {
            Response element = houses.getResponse().get(position);
            Intent wonderDetailActivityIntent = new Intent(getContext(), HouseDetailActivity.class);
            wonderDetailActivityIntent.putExtra(HouseDetailActivity.EXTRA_DATA, element);
            startActivity(wonderDetailActivityIntent);
            adapter.notifyDataSetChanged();
        };
        adapter = new HouseAdapter(getContext(), houses, adapterI);
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
        return new HouseTask(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Houses> loader, Houses data) {
        adapter.setResponses(data);
        houses = data;
        adapter.notifyDataSetChanged();
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

    public static interface OnFragmentInteractionListener {

    }
}
