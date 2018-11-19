package com.example.sestefan.proyecto.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.domain.Fotos;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseImagesAdapter extends RecyclerView.Adapter<HouseImagesAdapter.HouseImagesViewHolder> {

    private ArrayList<Fotos> houseImages;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public HouseImagesAdapter(Context context, ArrayList<Fotos> houseImages, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.houseImages = houseImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HouseImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.house_detail_card_item, parent, false);
        return new HouseImagesViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseImagesViewHolder holder, int position) {
        if (houseImages.size() > 0) {
            String current = houseImages.get(position).getInmuebleImagenUrl();
            if (current != null && current.length() > 0) {
                Picasso.get().load(current).into(holder.getImgHouse());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (houseImages.size() == 0) {
            return 1;
        } else {
            return houseImages.size();
        }
    }

    public class HouseImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imgHouse;

        private RecyclerViewClickListener listener;

        public HouseImagesViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.imgHouse = itemView.findViewById(R.id.img_house_detail);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onClick(v, position);
        }

        public ImageView getImgHouse() {
            return imgHouse;
        }
    }
}
