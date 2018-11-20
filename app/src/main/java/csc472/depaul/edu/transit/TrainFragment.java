package csc472.depaul.edu.transit;

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

import java.util.ArrayList;

import csc472.depaul.edu.transit.Train.CTATrainDatabase;
import csc472.depaul.edu.transit.Train.CTATrainRequestThread;
import csc472.depaul.edu.transit.Train.CTATrainStop;

public class TrainFragment extends Fragment {

    CTATrainDatabase database;
    ArrayList<CTATrainStop> allStops;
    String chosenStop;

    CTATrainRequestThread newThread = new CTATrainRequestThread();
    Thread t1 = new Thread(newThread);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cta_train, container, false);

        database = CTATrainDatabase.getInstance(container.getContext());
        allStops = database.getAllStops();

        ArrayList<String> allStopsText = new ArrayList<>();


        Button stopButton = v.findViewById(R.id.pick_cta_stop);
        Button searchButton = v.findViewById(R.id.button_cta_stop);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> allStopsText = new ArrayList<>();

                for (CTATrainStop stop : allStops) {
                    allStopsText.add(stop.getStopName());
                }

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.cta_train_spinner, null);
                mBuilder.setTitle("Select CTA Train Stop");
                final Spinner mSpinner = mView.findViewById(R.id.cta_stop_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, allStopsText);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chosenStop = mSpinner.getSelectedItem().toString();
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenStop != null) {
                    for (CTATrainStop stop : allStops) {
                        if (stop.getStopName().equals(chosenStop)) {
                            int id = stop.getStopID();
                            newThread.pushRequests(id);
                        }
                    }
                }
            }
        });

        return v;
    }
}
