package com.example.sestefan.proyecto.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListHouseImagesAdapter extends RecyclerView.Adapter<ListHouseImagesAdapter.HouseImagesViewHolder> {

    private ArrayList<String> houseImages;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public ListHouseImagesAdapter(Context context, ArrayList<String> houseImages, RecyclerViewClickListener listener) {
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
        String current = houseImages.get(position);
        Picasso.get().load(current).into(holder.imgWorldWonder);
    }

    @Override
    public int getItemCount() {
        if (houseImages != null) {
            return houseImages.size();
        } else {
            return 0;
        }
    }

    public class HouseImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imgWorldWonder;
        private RecyclerViewClickListener listener;

        public HouseImagesViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.imgWorldWonder = itemView.findViewById(R.id.img_country);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onClick(v, position);
        }
    }
}
