package csc472.depaul.edu.transit.CTABuses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import csc472.depaul.edu.transit.R;

public class BusActivity extends AppCompatActivity {

    BusRequestThread bus = new BusRequestThread();

    Thread t1 = new Thread(bus);

    TextView RouteName;
    TextView Direction;
    TextView StartStop;
    TextView DestinationStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        t1.start();

        RouteName = findViewById(R.id.RouteName);
        Direction = findViewById(R.id.Direction);
        StartStop = findViewById(R.id.StartStop);
        DestinationStop = findViewById(R.id.DestinationStop);
        Button Submit = findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] forum = {RouteName.getText().toString(),
                                    Direction.getText().toString(),
                                    StartStop.getText().toString(),
                                        DestinationStop.getText().toString()};
                bus.request(forum);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause(){
        super.onPause();
    }




}
