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

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder> {

    private ArrayList<String> rooms;
    private LayoutInflater inflater;
    private RecyclerViewClickListener listener;

    public RoomsAdapter(Context context, ArrayList<String> neighborhood, RecyclerViewClickListener listener) {
        inflater = LayoutInflater.from(context);
        this.rooms = neighborhood;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = inflater.inflate(R.layout.filter_room, parent, false);
        return new RoomsViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, int position) {
        TextView txtRoom = holder.getTxtRooms();
        txtRoom.setText(rooms.get(position));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class RoomsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txtRooms;

        private RecyclerViewClickListener listener;

        public RoomsViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.txtRooms = itemView.findViewById(R.id.txt_filter_room);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            listener.onClick(v, position);
        }

        public TextView getTxtRooms() {
            return txtRooms;
        }

    }
}
