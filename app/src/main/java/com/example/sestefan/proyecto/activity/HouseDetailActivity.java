package com.example.sestefan.proyecto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Habitaciones;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.HouseImagesAdapter;

import java.util.ArrayList;

public class HouseDetailActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_detail_activity);

        Intent intent = getIntent();
        data = intent.getParcelableExtra(EXTRA_DATA);

        txtRoom = findViewById(R.id.txt_room);
        txtBathroom = findViewById(R.id.txt_bath);
        txtDims = findViewById(R.id.txt_dims);
        txtTitle = findViewById(R.id.txt_title);

        chkGarage = findViewById(R.id.chk_garage);
        chkBarbecue = findViewById(R.id.chk_barbecue);
        chkBalcony = findViewById(R.id.chk_balcony);
        chkPlayground = findViewById(R.id.chk_playground);

        txtRoom.setText(data.getInmuebleCantDormitorio());
        txtBathroom.setText(getBathQty());
        txtDims.setText(data.getInmuebleMetrosCuadrados());
        txtTitle.setText(data.getInmuebleTitulo());

        chkGarage.setChecked(Boolean.valueOf(data.getInmuebleTieneGarage()));
        chkBarbecue.setChecked(Boolean.valueOf(data.getInmuebleTieneParrillero()));
        chkBalcony.setChecked(Boolean.valueOf(data.getInmuebleTieneBalcon()));
        chkPlayground.setChecked(Boolean.valueOf(data.getInmuebleTienePatio()));


        recyclerView = findViewById(R.id.recycledView2);

        RecyclerViewClickListener adapterI = (view, position) -> {
        };
        adapter = new HouseImagesAdapter(this, data.getFotos(), adapterI);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

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
