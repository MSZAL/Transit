package csc472.depaul.edu.transit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import csc472.depaul.edu.transit.database.MetraDatabase;
import csc472.depaul.edu.transit.database.StationInfo;
import csc472.depaul.edu.transit.database.TripInfo;

public class MetraDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metra_display);

        MetraDatabase database = MetraDatabase.getInstance(getApplicationContext());
        MetraInfo metraInfo = getIntent().getExtras().getParcelable("metraValues");

        String line = metraInfo.getLine();
        String departure = metraInfo.getDeparture();
        String destination = metraInfo.getDestination();

        ArrayList<TripInfo> trips = database.getTimes(line, departure, destination);
        HashMap<String, ArrayList<StationInfo>> stops = new HashMap<>();
        for (TripInfo trip : trips) {
            stops.put(trip.getTripId(), trip.getStations());
        }

        ExpandableListView listView = findViewById(R.id.listView);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, trips, stops);
        listView.setAdapter(listAdapter);
    }
}
