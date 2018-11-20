package csc472.depaul.edu.transit.metra.database;

import java.util.ArrayList;

public class TripInfo {

    private ArrayList<StationInfo> stations;
    private String tripId;

    public TripInfo(ArrayList<StationInfo> stations, String tripId) {
        this.stations = stations;
        this.tripId = tripId;
    }

    public ArrayList<StationInfo> getStations() {
        return stations;
    }

    public String getTripId() {
        return tripId;
    }

    public String getStartTime() {
        return stations.get(0).getTime();
    }

    public String getEndTime() {
        return stations.get(stations.size()-1).getTime();
    }

    public int getTripLength() {
        return stations.size();
    }
}
