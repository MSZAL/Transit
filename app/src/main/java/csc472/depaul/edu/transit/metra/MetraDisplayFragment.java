package csc472.depaul.edu.transit.metra;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import csc472.depaul.edu.transit.R;
import csc472.depaul.edu.transit.metra.database.MetraDatabase;
import csc472.depaul.edu.transit.metra.database.StationInfo;
import csc472.depaul.edu.transit.metra.database.TripInfo;

public class MetraDisplayFragment extends Fragment {

    private MetraInfo metraInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View metraView =  inflater.inflate(R.layout.activity_metra_display, container, false);

        MetraDatabase database = MetraDatabase.getInstance(metraView.getContext());

        String line = metraInfo.getLine();
        String departure = metraInfo.getDeparture();
        String destination = metraInfo.getDestination();

        ArrayList<TripInfo> trips = database.getTimes(line, departure, destination);
        HashMap<String, ArrayList<StationInfo>> stops = new HashMap<>();
        for (TripInfo trip : trips) {
            stops.put(trip.getTripId(), trip.getStations());
        }

        ExpandableListView listView = metraView.findViewById(R.id.listView);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(metraView.getContext(), trips, stops);
        listView.setAdapter(listAdapter);

        return metraView;
    }

    public void updateMetraValues(MetraInfo metraInfo) {
        this.metraInfo = metraInfo;
    }

}
