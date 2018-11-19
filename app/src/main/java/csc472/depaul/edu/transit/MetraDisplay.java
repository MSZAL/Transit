package csc472.depaul.edu.transit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import csc472.depaul.edu.transit.database.MetraDatabase;
import csc472.depaul.edu.transit.database.TripInfo;

public class MetraDisplay extends AppCompatActivity {

    MetraDatabase database;
    MetraInfo metraInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metra_display);

        database = MetraDatabase.getInstance(getApplicationContext());
        metraInfo = getIntent().getExtras().getParcelable("metraValues");

        String line = metraInfo.getLine();
        String departure = metraInfo.getDeparture();
        String destination = metraInfo.getDestination();

        ArrayList<TripInfo> times = database.getTimes(line, departure, destination);

        Log.d("SQLITE", "Amount of routes: " + times.size());
        for (TripInfo time : times) {
            Log.d("SQLITE", "Trip length: " + time.getTripLength() + ", TripId: " + time.getTripId() + ", StartTime: " + time.getStartTime() + ", EndTime: " + time.getEndTime());
        }

    }
}
