package csc472.depaul.edu.transit.CTATrains;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class CTATrainActivity extends AppCompatActivity {

    CTATrainDatabase database;

    CTATrainRequestThread newThread = new CTATrainRequestThread();
    Thread t1 = new Thread(newThread);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        setTitle("CTA TRAINS BETA");

        t1.start();

        database = CTATrainDatabase.getInstance(getApplicationContext());

        ArrayList<CTATrainStop> allStops = database.getAllStops();
        for (int i = 0; i < allStops.size(); i++){
            Log.i("allStops", allStops.get(i).getStopName());
        }


        final int StopChoice = 30236;


        newThread.pushRequests(StopChoice);






    }


}
