package csc472.depaul.edu.transit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import csc472.depaul.edu.transit.database.MetraDatabase;

public class MainActivity extends AppCompatActivity {

    MetraDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MetraDatabase.getInstance(getApplicationContext());
        testDatabase();
    }

    /* Tests the database */
    private void testDatabase() {
        database.testQuery();
    }
}
