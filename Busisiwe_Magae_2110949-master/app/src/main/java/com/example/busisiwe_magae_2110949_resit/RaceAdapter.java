package com.example.busisiwe_magae_2110949_resit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.RaceViewHolder> {

    private List<Race> races;

    public RaceAdapter(List<Race> races) {
        this.races = races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    @NonNull
    @Override
    public RaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.race_item, parent, false);
        return new RaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaceViewHolder holder, int position) {
        Race race = races.get(position);
        holder.textViewRaceName.setText(race.getRaceName());
        holder.textViewCircuitName.setText(race.getCircuitName());
        holder.textViewDate.setText(race.getDate());
    }

    @Override
    public int getItemCount() {
        return races.size();
    }

    public static class RaceViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRaceName;
        public TextView textViewCircuitName;
        public TextView textViewDate;

        public RaceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRaceName = itemView.findViewById(R.id.textViewRaceName);
            textViewCircuitName = itemView.findViewById(R.id.textViewCircuitName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
