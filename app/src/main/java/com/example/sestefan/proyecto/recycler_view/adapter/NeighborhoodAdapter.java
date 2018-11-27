package com.example.sestefan.proyecto.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sestefan.proyecto.R;
import com.example.sestefan.proyecto.recycler_view.RecyclerViewClickListener;

import java.util.ArrayList;

public class NeighborhoodAdapter extends RecyclerView.Adapter<NeighborhoodAdapter.NeigborhoodViewHolder> {

    private ArrayList<String> neighborhoods;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public NeighborhoodAdapter(Context context, ArrayList<String> neighborhood, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.neighborhoods = neighborhood;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NeigborhoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.filter_neighborhood, parent, false);
        return new NeigborhoodViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NeigborhoodViewHolder holder, int position) {
        TextView txtNeighborhood = holder.getTxtNeighborhood();
        txtNeighborhood.setText(neighborhoods.get(position));
    }

    @Override
    public int getItemCount() {
        return neighborhoods.size();
    }

    public class NeigborhoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtNeighborhood;

        private RecyclerViewClickListener listener;

        public NeigborhoodViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.txtNeighborhood = itemView.findViewById(R.id.txt_filter_neighborhood);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onClick(v, position);
        }

        public TextView getTxtNeighborhood() {
            return txtNeighborhood;
        }

    }
}
