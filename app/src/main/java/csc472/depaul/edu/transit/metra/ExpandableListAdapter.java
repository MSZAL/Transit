package csc472.depaul.edu.transit.metra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

import csc472.depaul.edu.transit.R;
import csc472.depaul.edu.transit.metra.database.StationInfo;
import csc472.depaul.edu.transit.metra.database.TripInfo;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<TripInfo> trips;
    private HashMap<String, ArrayList<StationInfo>> stops;

    public ExpandableListAdapter(Context context, ArrayList<TripInfo> trips, HashMap<String, ArrayList<StationInfo>> stops) {
        this.context = context;
        this.trips = trips;
        this.stops = stops;
    }

    @Override
    public int getGroupCount() {
        return trips.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return stops.get(trips.get(groupPosition).getTripId()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return trips.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return stops.get(trips.get(groupPosition).getTripId()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private TripInfo getTrip(int groupPosition) {
        return trips.get(groupPosition);
    }

    private StationInfo getStation(int groupPosition, int childPosition) {
        return stops.get(trips.get(groupPosition).getTripId()).get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String tripId = getTrip(groupPosition).getTripId();
        String startTime = getTrip(groupPosition).getStartTime();
        String endTime = getTrip(groupPosition).getEndTime();
        String stops = new StringBuilder().append(Integer.toString(getTrip(groupPosition).getTripLength())).append(" Stops").toString();
        String time = new StringBuilder().append(startTime).append(" ➔ ").append(endTime).toString();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_metra, null);
        }

        TextView tripView = convertView.findViewById(R.id.group_tripid);
        tripView.setText(tripId);

        TextView stopView = convertView.findViewById(R.id.group_number_of_stops);
        stopView.setText(stops);

        TextView timeView = convertView.findViewById(R.id.group_time);
        timeView.setText(time);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        StationInfo station = getStation(groupPosition, childPosition);

        String stop = station.getName();
        String time = station.getTime();
        String zone = new StringBuilder().append("Zone: ").append(station.getZone()).toString();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_metra, null);
        }

        TextView stopView = convertView.findViewById(R.id.item_stop);
        stopView.setText(stop);

        TextView zoneView = convertView.findViewById(R.id.item_zone);
        zoneView.setText(zone);

        TextView timeView = convertView.findViewById(R.id.item_time);
        timeView.setText(time);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
