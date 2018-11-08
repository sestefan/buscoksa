package com.example.sestefan.proyecto.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Habitaciones;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseImagesAdapter;

import java.util.ArrayList;

public class HouseDetailFragment extends Fragment {

    public static final String EXTRA_DATA = "extra_data";

    private RecyclerView recyclerView;
    private HouseImagesAdapter adapter;

    private Response data;

    private TextView txtRoom;
    private TextView txtBathroom;
    private TextView txtDims;
    private TextView txtTitle;

    private CheckBox chkGarage;
    private CheckBox chkBarbecue;
    private CheckBox chkBalcony;
    private CheckBox chkPlayground;

    private OnFragmentInteractionListener mListener;

    public HouseDetailFragment() {
        // Required empty public constructor
    }

    public static HouseDetailFragment newInstance(Response data) {
        HouseDetailFragment fragment = new HouseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getParcelable(EXTRA_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_house_detail, container, false);

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
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
}
