package csc472.depaul.edu.transit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //just uses button to go to the map activity
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityMap();
            }
        });
    }

    //opens the map activity
    public void openActivityMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
