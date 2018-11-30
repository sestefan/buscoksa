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
import android.widget.Switch;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.HouseDTO;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Neighborhood;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.NeighborhoodAdapter;
import com.example.sestefan.proyecto.recycler_view.adapter.RoomsAdapter;
import com.github.florent37.androidslidr.Slidr;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {

    private Slidr slidr;

    private Switch swGarage;
    private Switch swBarbecue;

    private HouseDTO filter;

    private ArrayList<Neighborhood> neighborhoods;

    private String neighborhood;
    private String room;

    private Houses houses;

    private NeighborhoodAdapter adapter;

    private RoomsAdapter adapter2;

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

        swGarage = v.findViewById(R.id.sw_filter_garage);
        swBarbecue = v.findViewById(R.id.sw_filter_barbecue);

        RecyclerView recyclerView = v.findViewById(R.id.recycledView3);

        RecyclerViewClickListener adapterI = (v1, position) -> {
            TextView textView = v1.findViewById(R.id.txt_filter_neighborhood);
//            if (textView.getBackground() instanceof ColorDrawable) {
//                ColorDrawable cd = (ColorDrawable) textView.getBackground();
//                if (cd.getColor() == Color.RED) {
//                    textView.setBackgroundResource(R.color.white);
//                } else {
//                    textView.setBackgroundResource(R.color.red);
//                }
//            }
            neighborhood = textView.getText().toString();
        };
        adapter = new NeighborhoodAdapter(getContext(), getNeighborhoods(), adapterI);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recyclerView2 = v.findViewById(R.id.recyclerView4);

        RecyclerViewClickListener adapterI2 = (v1, position) -> {
            TextView textView = v1.findViewById(R.id.txt_filter_room);
//            if (textView.getBackground() instanceof ColorDrawable) {
//                ColorDrawable cd = (ColorDrawable) textView.getBackground();
//                if (cd.getColor() == Color.RED) {
//                    textView.setBackgroundResource(R.color.white);
//                } else {
//                    textView.setBackgroundResource(R.color.red);
//                }
//            }
            room = textView.getText().toString();
        };
        adapter2 = new RoomsAdapter(getContext(), getRooms(), adapterI2);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if (filter != null) {
            slidr.setCurrentValue(Float.valueOf(filter.getPrecio()));
            swGarage.setChecked(Boolean.valueOf(filter.getTieneGarage()));
            swBarbecue.setChecked(Boolean.valueOf(filter.getTieneParrillero()));
            neighborhood = filter.getBarrio();
            room = filter.getCantDormitorio();
        }

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
        filter.setBarrio(neighborhood);
        filter.setTieneGarage(String.valueOf(swGarage.isChecked()));
        filter.setTieneParrillero(String.valueOf(swBarbecue.isChecked()));
        filter.setCantDormitorio(room);

        data.putExtra("dataFilter", filter);
        getTargetFragment().onActivityResult(1, 200, data);
        dismiss();
    }

    private void cancel() {
        getTargetFragment().onActivityResult(1, 200, null);
        dismiss();
    }

    private ArrayList<Neighborhood> loadNeighborhood() {
        ArrayList<Neighborhood> neighborhoods = new ArrayList<>();
        return neighborhoods;
    }

    @NonNull
    private ArrayList<String> getNeighborhoods() {
        ArrayList<String> neighborhoods = new ArrayList<>();
        if (houses.getResponse().size() == 0) {
            this.neighborhoods = loadNeighborhood();
            for (Neighborhood neighborhood : this.neighborhoods) {
                neighborhoods.add(neighborhood.getName());
            }
        } else {
            for (Response response : houses.getResponse()) {
                neighborhoods.add(response.getInmuebleBarrio());
            }
        }
        return neighborhoods;
    }

    @NonNull
    private ArrayList<String> getRooms() {
        ArrayList<String> rooms;
        rooms = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            rooms.add(String.valueOf(i));
        }
        return rooms;
    }
}
