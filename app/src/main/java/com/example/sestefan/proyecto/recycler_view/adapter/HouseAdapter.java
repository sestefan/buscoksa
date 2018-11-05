package com.example.sestefan.proyecto.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Fotos;
import com.example.sestefan.proyecto.domain.Houses;
import com.example.sestefan.proyecto.domain.Response;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private static final String DEFAULT_HOUSE_PIC_URL = "https://www.google.com.uy/imgres?imgurl=https%3A%2F%2Fwww.aashiyanaforme.com%2Fwp-content%2Fuploads%2F2017%2F10%2Fno-photo-available-12.jpg&imgrefurl=https%3A%2F%2Fwww.aashiyanaforme.com%2Fproperties%2F2-bhak-builder-floor-at-vikasnagar-2%2F&docid=d-uB6XUdM4mxlM&tbnid=vT6gen6KGB560M%3A&vet=10ahUKEwiHnfPVwrPeAhVEQZAKHWO4AxQQMwhaKBQwFA..i&w=440&h=330&itg=1&bih=994&biw=1919&q=no%20image%20default%20%20photo&ved=0ahUKEwiHnfPVwrPeAhVEQZAKHWO4AxQQMwhaKBQwFA&iact=mrc&uact=8";

    private Houses houses;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public HouseAdapter(Context context, Houses houses, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.houses = houses;
        this.listener = listener;
    }

    public void setResponses(Houses houses) {
        this.houses = houses;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.house_card_item, parent, false);
        return new HouseViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        Response current = houses.getResponse().get(position);
        holder.getTxtPrice().setText("Price: " + current.getInmueblePrecio());
        holder.getTxtNeighborhood().setText("Neighborhood: " + current.getInmuebleBarrio());
        holder.getTxtRooms().setText("#rooms: " + current.getInmuebleCantDormitorio());
        ArrayList<Fotos> images = current.getFotos();
        if (images != null && images.size() > 0) {
            Picasso.get().load(images.get(0).getInmuebleImagenUrl()).into(holder.getImgHouse());
        } else {
            Picasso.get().load(R.drawable.default_home).into(holder.getImgHouse());
        }
    }

    @Override
    public int getItemCount() {
        if (houses != null) {
            return houses.getResponse() == null ? 0 : houses.getResponse().size();
        } else {
            return 0;
        }
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtNeighborhood;
        private final TextView txtPrice;
        private final TextView txtRooms;

        private final ImageView imgHouse;

        private RecyclerViewClickListener listener;

        public HouseViewHolder(View itemView, RecyclerViewClickListener listener) {

            super(itemView);
            itemView.setOnClickListener(this);

            this.txtNeighborhood = itemView.findViewById(R.id.txt_price);
            this.txtPrice = itemView.findViewById(R.id.txt_neighborhood);
            this.txtRooms = itemView.findViewById(R.id.txt_rooms);
            this.imgHouse = itemView.findViewById(R.id.img_house);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onClick(v, position);
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public TextView getTxtRooms() {
            return txtRooms;
        }

        public TextView getTxtNeighborhood() {
            return txtNeighborhood;
        }

        public ImageView getImgHouse() {
            return imgHouse;
        }
    }
}
