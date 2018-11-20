package csc472.depaul.edu.transit.metra;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import csc472.depaul.edu.transit.R;
import csc472.depaul.edu.transit.metra.database.MetraDatabase;

public class MetraHome extends AppCompatActivity {

    MetraDatabase database;
    Button showLines, showDeparture, showDestination, search;
    String userLine, userDeparture, userDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metra_home);

        database = MetraDatabase.getInstance(getApplicationContext());

        /* Sets the Metra Line button */
        showLines = findViewById(R.id.lineBtn);
        showLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MetraHome.this);
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select a Metra train line");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MetraHome.this,
                        android.R.layout.simple_spinner_dropdown_item, database.getMetraLines());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userLine = mSpinner.getSelectedItem().toString();
                        dialog.dismiss();
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        /* Sets the Departure button */
        showDeparture = findViewById(R.id.departureBtn);
        showDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MetraHome.this);
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select your departure");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MetraHome.this,
                        android.R.layout.simple_spinner_dropdown_item, database.getStops(userLine));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDeparture = mSpinner.getSelectedItem().toString();
                        dialog.dismiss();
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        /* Sets the Destination button */
        showDestination = findViewById(R.id.destinationBtn);
        showDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MetraHome.this);
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select your destination");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MetraHome.this,
                        android.R.layout.simple_spinner_dropdown_item, database.getStops(userLine));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDestination = mSpinner.getSelectedItem().toString();
                        dialog.dismiss();
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        /* Sets up the search button */
        search = findViewById(R.id.searchBtn);
        if (search != null) {
            search.setOnClickListener(onClickSearch);
        }

    }

    /* On click it goes to next activity */
    private View.OnClickListener onClickSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (userLine != null && userDeparture != null && userDestination != null) {
                MetraInfo metra = new MetraInfo(userLine, userDeparture, userDestination);
                Bundle bundle = new Bundle();
                bundle.putParcelable("metraValues", metra);

                Intent intent = new Intent(getBaseContext(), MetraDisplay.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                String sToastMessage = "You need to select a choice from each category";
                Toast toast = Toast.makeText(getApplicationContext(), sToastMessage, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

}
