package csc472.depaul.edu.transit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import csc472.depaul.edu.transit.database.MetraDatabase;
import csc472.depaul.edu.transit.database.StationInfo;
import csc472.depaul.edu.transit.database.TripInfo;

public class MainActivity extends AppCompatActivity {

    MetraDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String metraLine, departure, destination;

        /* Gets an instance of the database */
        database = MetraDatabase.getInstance(getApplicationContext());

        /* Gets all the Metra lines */
        ArrayList<String> metraLines = database.getMetraLines();
        metraLine = metraLines.get(0);

        /* Get all the stops associated with the line the user picks */
        ArrayList<String> metraStops = database.getStops(metraLine);
        departure = metraStops.get(0);
        destination = metraStops.get(1);

        /* Gets all the trips for the day */
        ArrayList<TripInfo> trips = database.getTimes(metraLine, departure, destination);

        /* The trips ArrayList holds all the data needed for the Metra part */
    }

}
