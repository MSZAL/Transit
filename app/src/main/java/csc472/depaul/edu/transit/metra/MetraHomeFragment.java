package csc472.depaul.edu.transit.metra;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import csc472.depaul.edu.transit.R;
import csc472.depaul.edu.transit.metra.database.MetraDatabase;

public class MetraHomeFragment extends Fragment {

    public interface MetraHomeListener {
        void metraDisplayValues(MetraInfo input);
    }

    private MetraHomeListener listener;
    private MetraDatabase database;
    private String userLine, userDeparture, userDestination;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View metraView =  inflater.inflate(R.layout.activity_metra_home, container, false);

        database = MetraDatabase.getInstance(metraView.getContext());

        /* Sets the Metra Line button */
        Button showLines = metraView.findViewById(R.id.lineBtn);
        showLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(metraView.getContext());
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select a Metra train line");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(metraView.getContext(),
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
        Button showDeparture = metraView.findViewById(R.id.departureBtn);
        showDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(metraView.getContext());
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select your departure");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(metraView.getContext(),
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
        Button showDestination = metraView.findViewById(R.id.destinationBtn);
        showDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(metraView.getContext());
                View mView = getLayoutInflater().inflate(R.layout.spinner_metra, null);
                mBuilder.setTitle("Select your destination");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(metraView.getContext(),
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
        Button search = metraView.findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userLine != null && userDeparture != null && userDestination != null) {
                    MetraInfo metraInfo = new MetraInfo(userLine, userDeparture, userDestination);
                    listener.metraDisplayValues(metraInfo);
                } else {
                    String sToastMessage = "You need to select a choice from each category";
                    Toast toast = Toast.makeText(metraView.getContext(), sToastMessage, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return metraView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MetraHomeListener) {
            listener = (MetraHomeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MetraHomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
