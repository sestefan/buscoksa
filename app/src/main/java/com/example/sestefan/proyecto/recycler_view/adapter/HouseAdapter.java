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

    private Houses houses;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    private boolean isUserLoggedIn;
    private boolean isLayoutNormal;

    public HouseAdapter(Context context, Houses houses, boolean isUserLoggedIn, boolean isLayoutNormal, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.houses = houses;
        this.listener = listener;
        this.isUserLoggedIn = isUserLoggedIn;
        this.isLayoutNormal = isLayoutNormal;
    }

    public void setResponses(Houses houses) {
        this.houses = houses;
    }

    public void setLayoutNormal(boolean layoutNormal) {
        isLayoutNormal = layoutNormal;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (isLayoutNormal) {
            itemView = inflater.inflate(R.layout.house_card_item, parent, false);
        } else {
            itemView = inflater.inflate(R.layout.house_card_item_2, parent, false);
        }
        return new HouseViewHolder(itemView, listener, isUserLoggedIn);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        Response current = houses.getResponse().get(position);
        holder.getTxtPrice().setText(String.format("%s %s", inflater.getContext().getString(R.string.item_price), current.getInmueblePrecio()));
        holder.getTxtNeighborhood().setText(String.format("%s %s", inflater.getContext().getString(R.string.item_neighborhood), current.getInmuebleBarrio()));
        holder.getTxtRooms().setText(String.format("%s %s", inflater.getContext().getString(R.string.item_rooms), current.getInmuebleCantDormitorio()));
        ImageView imgFavorite = holder.getImgFavorite();
        ArrayList<Fotos> images = current.getFotos();
        if (images != null && images.size() > 0) {
            Picasso.get().load(images.get(0).getInmuebleImagenUrl()).into(holder.getImgHouse());
        } else {
            Picasso.get().load(R.drawable.default_home).into(holder.getImgHouse());

        }
        if (holder.isUserLoggedIn()) {
            if (current.isFavorito()) {
                Picasso.get().load(R.drawable.baseline_favorite_white_24dp).into(imgFavorite);
            }
        } else {
            imgFavorite.setEnabled(false);
            imgFavorite.setVisibility(View.INVISIBLE);
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

        private final ImageView imgFavorite;

        private RecyclerViewClickListener listener;

        private boolean isUserLoggedIn;

        public HouseViewHolder(View itemView, RecyclerViewClickListener listener, boolean isUserLoggedIn) {

            super(itemView);
            itemView.setOnClickListener(this);

            this.txtNeighborhood = itemView.findViewById(R.id.txt_price);
            this.txtPrice = itemView.findViewById(R.id.txt_neighborhood);
            this.txtRooms = itemView.findViewById(R.id.txt_rooms);
            this.imgHouse = itemView.findViewById(R.id.img_house);
            this.imgFavorite = itemView.findViewById(R.id.imgFavorite);
            this.listener = listener;
            this.isUserLoggedIn = isUserLoggedIn;
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

        public ImageView getImgFavorite() {
            return imgFavorite;
        }

        public boolean isUserLoggedIn() {
            return isUserLoggedIn;
        }
    }
}
