//package com.example.busisiwe_magae_2110949_resit;
//
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.view.MenuItem;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.StringReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    public static final String DRIVER_STANDINGS_URL = "https://example.com/driverStandings";
//    public static final String RACE_SCHEDULE_URL = "https://example.com/raceSchedule";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Allow network operation on the main thread for simplicity (not recommended for production apps)
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
//
//        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//        // Load the default fragment
//        loadFragment(new DriverStandingsFragment());
//    }
//
//    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment = null;
//            switch (item.getItemId()) {
//                case R.id.navigation_drivers:
//                    fragment = new DriverStandingsFragment();
//                    break;
//                case R.id.navigation_schedule:
//                    fragment = new RaceScheduleFragment();
//                    break;
//                default:
//                    return false;
//            }
//            return loadFragment(fragment);
//        }
//    };
//
//    private boolean loadFragment(Fragment fragment) {
//        // switch the fragment
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
//
//    // Fetch data from the API
//    public String getDataFromApi(String urlString) {
//        StringBuilder result = new StringBuilder();
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            int data;
//            try (var reader = new InputStreamReader(connection.getInputStream())) {
//                while ((data = reader.read()) != -1) {
//                    result.append((char) data);
//                }
//            }
//            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//
//    // Parse driver standings from XML
//    public List<Driver> parseDriverStandings(String xml) {
//        List<Driver> drivers = new ArrayList<>();
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(new StringReader(xml));
//
//            int eventType = parser.getEventType();
//            Driver currentDriver = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tagName = parser.getName();
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        if ("driver".equalsIgnoreCase(tagName)) {
//                            currentDriver = new Driver();
//                        } else if (currentDriver != null) {
//                            if ("name".equalsIgnoreCase(tagName)) {
//                                currentDriver.setName(parser.nextText());
//                            } else if ("team".equalsIgnoreCase(tagName)) {
//                                currentDriver.setTeam(parser.nextText());
//                            } else if ("points".equalsIgnoreCase(tagName)) {
//                                currentDriver.setPoints(Integer.parseInt(parser.nextText()));
//                            }
//                        }
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        if ("driver".equalsIgnoreCase(tagName) && currentDriver != null) {
//                            drivers.add(currentDriver);
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return drivers;
//    }
//
//    // Parse race schedule from XML
//    public List<Race> parseRaceSchedule(String xml) {
//        List<Race> races = new ArrayList<>();
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = factory.newPullParser();
//            parser.setInput(new StringReader(xml));
//
//            int eventType = parser.getEventType();
//            Race currentRace = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tagName = parser.getName();
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        if ("race".equalsIgnoreCase(tagName)) {
//                            currentRace = new Race();
//                        } else if (currentRace != null) {
//                            if ("location".equalsIgnoreCase(tagName)) {
//                                currentRace.setLocation(parser.nextText());
//                            } else if ("date".equalsIgnoreCase(tagName)) {
//                                currentRace.setDate(parser.nextText());
//                            }
//                        }
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        if ("race".equalsIgnoreCase(tagName) && currentRace != null) {
//                            races.add(currentRace);
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return races;
//    }
//}

package com.example.busisiwe_magae_2110949_resit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity {
    public static final String DRIVER_STANDINGS_URL = "http://ergast.com/api/f1/current/driverStandings";
    public static final String RACE_SCHEDULE_URL = "http://ergast.com/api/f1/2024";

    private DriverStandingsFragment driverStandingsFragment;
    private RaceScheduleFragment raceScheduleFragment;
    public DriverManager driverManager;
    public RaceManager raceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Initialize fragments
        driverStandingsFragment = new DriverStandingsFragment(this);
        raceScheduleFragment = new RaceScheduleFragment(this);

        // Initialize managers
        driverManager = new DriverManager();
        raceManager = new RaceManager();

        // Load the default fragment
        loadFragment(driverStandingsFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_drivers) {
                loadFragment(driverStandingsFragment);
            } else if (itemId == R.id.navigation_schedule) {
                loadFragment(raceScheduleFragment);
            } else {
                return false;
            }
            return true;
        }
    };

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Fetch data from the API asynchronously
    public void fetchDataFromApi(String url, OnDataFetchedListener listener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return getDataFromApi(url);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error fetching data from API", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    Log.d("MainActivity", "API response: " + result);
                    listener.onDataFetched(result);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching data from API", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private String getDataFromApi(String urlString) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int data;
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                while ((data = reader.read()) != -1) {
                    result.append((char) data);
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public interface OnDataFetchedListener {
        void onDataFetched(String data);
    }
}
