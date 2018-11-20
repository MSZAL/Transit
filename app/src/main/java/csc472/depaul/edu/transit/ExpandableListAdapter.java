package csc472.depaul.edu.transit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import csc472.depaul.edu.transit.database.StationInfo;
import csc472.depaul.edu.transit.database.TripInfo;

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(tripId);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        StationInfo station = getStation(groupPosition, childPosition);
        final String childText = station.getName();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView textView = convertView.findViewById(R.id.itemId);
        textView.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
