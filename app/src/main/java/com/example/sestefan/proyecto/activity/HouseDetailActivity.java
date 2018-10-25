package com.example.sestefan.proyecto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.House;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.example.sestefan.proyecto.recycler_view.adapter.ListHouseImagesAdapter;

public class HouseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private RecyclerView recyclerView;
    private ListHouseImagesAdapter adapter;

    private House data;

    private TextView txtName;
    private TextView txtCountry;
    private TextView txtEpoch;
    private TextView txtExtraInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_detail_activity);

        Intent intent = getIntent();
        data = intent.getParcelableExtra(EXTRA_DATA);

        txtName = findViewById(R.id.txt_name);
        txtCountry = findViewById(R.id.txt_country);
        txtEpoch = findViewById(R.id.txt_epoch);
        txtExtraInfo = findViewById(R.id.txt_extra_info);

        txtName.setText(data.getNombre());
        txtCountry.setText(data.getPais());
        txtEpoch.setText(data.getEpoca());
        txtExtraInfo.setText(data.getExtraInfo());

        recyclerView = findViewById(R.id.recycledView2);

        RecyclerViewClickListener adapterI = (view, position) -> {
        };
        adapter = new ListHouseImagesAdapter(this, data.getImagenes(), adapterI);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

    }
}
