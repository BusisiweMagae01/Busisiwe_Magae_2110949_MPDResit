package com.example.busisiwe_magae_2110949_resit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RaceScheduleFragment extends Fragment {
    private RaceAdapter raceAdapter;
    private MainActivity mainActivity;

    public RaceScheduleFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_schedule, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewRaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        raceAdapter = new RaceAdapter(new ArrayList<>());
        recyclerView.setAdapter(raceAdapter);

        mainActivity.fetchDataFromApi(MainActivity.RACE_SCHEDULE_URL, this::onRaceScheduleDataFetched);
        return view;
    }

    private void onRaceScheduleDataFetched(String raceXml) {
        List<Race> races = mainActivity.raceManager.parseRaceSchedule(raceXml);
        if (races != null && !races.isEmpty()) {
            raceAdapter.setRaces(races);
            raceAdapter.notifyDataSetChanged();
        } else {
            // Handle the case where no races were parsed
            Toast.makeText(getContext(), "Unable to load race schedule", Toast.LENGTH_SHORT).show();
        }
    }
}

