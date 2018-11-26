package com.example.sestefan.proyecto.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.HouseDTO;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.NeighborhoodAdapter;
import com.github.florent37.androidslidr.Slidr;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {

    private Slidr slidr;

    private HouseDTO filter;

    private Houses houses;

    private NeighborhoodAdapter adapter;

    public static FilterDialogFragment newInstance(Houses houses, HouseDTO filter) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("houses", houses);
        args.putParcelable("filter", filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.houses = getArguments().getParcelable("houses");
            this.filter = getArguments().getParcelable("filter");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.filter_dialog_fragment, container, false);
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog_fragment, null);

        Button btnApply = v.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(view -> apply());

        Button btnCancel = v.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(view -> cancel());

        slidr = v.findViewById(R.id.seekBar);
        slidr.setMax(500000);
        slidr.setMin(10000);
        slidr.setTextFormatter(value -> String.format("%s %s", "U$S", (int) value));

        if (filter != null) {
            slidr.setCurrentValue(Float.valueOf(filter.getPrecio()));
        }

        RecyclerView recyclerView = v.findViewById(R.id.recycledView3);

        RecyclerViewClickListener adapterI = (view, position) -> {


            adapter.notifyDataSetChanged();
        };
        ArrayList<String> neighborhoods;
        neighborhoods = new ArrayList<>();
        for (Response response : houses.getResponse()) {
            neighborhoods.add(response.getInmuebleBarrio());
        }
        adapter = new NeighborhoodAdapter(getContext(), neighborhoods, adapterI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void apply() {
        Intent data = new Intent();
        if (filter == null) {
            filter = new HouseDTO();
            filter.setMaxResults("100");
        }
        filter.setPrecio(String.valueOf(slidr.getCurrentValue()));
        data.putExtra("dataFilter", filter);
        getTargetFragment().onActivityResult(1, 200, data);
        dismiss();
    }

    private void cancel() {
        getTargetFragment().onActivityResult(1, 200, null);
        dismiss();
    }


}
