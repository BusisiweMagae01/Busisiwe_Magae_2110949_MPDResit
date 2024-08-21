package com.example.busisiwe_magae_2110949_resit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DriverStandingsFragment extends Fragment {
    private DriverAdapter driverAdapter;
    private MainActivity mainActivity;

    public DriverStandingsFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_standings, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        driverAdapter = new DriverAdapter(new ArrayList<>());
        recyclerView.setAdapter(driverAdapter);

        mainActivity.fetchDataFromApi(MainActivity.DRIVER_STANDINGS_URL, this::onDriverStandingsDataFetched);
        return view;
    }

    private void onDriverStandingsDataFetched(String driverXml) {
        List<Driver> drivers = mainActivity.driverManager.parseDriverStandings(driverXml);
        driverAdapter.setDrivers(drivers);
        driverAdapter.notifyDataSetChanged();
    }
}

