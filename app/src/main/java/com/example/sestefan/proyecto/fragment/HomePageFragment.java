package com.example.sestefan.proyecto.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sestefan.proyecto.R;

public class HomePageFragment extends Fragment {
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
        return v;
    }
}
