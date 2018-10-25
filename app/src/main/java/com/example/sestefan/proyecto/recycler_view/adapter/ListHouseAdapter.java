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
import com.example.sestefan.proyecto.domain.House;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListHouseAdapter extends RecyclerView.Adapter<ListHouseAdapter.HouseViewHolder> {

    private LinkedList<House> houses;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public ListHouseAdapter(Context context, LinkedList<House> houses, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.houses = houses;
        this.listener = listener;
    }

    public void setHouses(LinkedList<House> houses) {
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
        House current = houses.get(position);
        holder.wordItemView.setText(current.getNombre());
        holder.txtCountry.setText(current.getPais());
        ArrayList<String> images = current.getImagenes();
        if (images != null && images.size() > 0) {
            Picasso.get().load(images.get(0)).into(holder.imgWorldWonder);
        }
    }

    @Override
    public int getItemCount() {
        if (houses != null) {
            return houses.size();
        } else {
            return 0;
        }
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView wordItemView;
        public final TextView txtCountry;
        public final ImageView imgWorldWonder;
        private RecyclerViewClickListener listener;

        public HouseViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.wordItemView = itemView.findViewById(R.id.txt_list_item);
            this.txtCountry = itemView.findViewById(R.id.txt_country);
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
