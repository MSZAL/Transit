package csc472.depaul.edu.transit.metra.database;

public class StationInfo {

    private String name;
    private String time;
    private String zone;
    private Coordinate location;

    public StationInfo(String name, String time, String zone, double longitude, double latitude) {
        this.name = name;
        this.time = time;
        this.zone = zone;
        this.location = new Coordinate(longitude, latitude);
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getZone() {
        return zone;
    }

    public Coordinate getLocation() {
        return location;
    }
}
