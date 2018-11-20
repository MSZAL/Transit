package csc472.depaul.edu.transit.metra.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StationInfo {

    private String name;
    private String time;
    private String zone;
    private Coordinate location;

    public StationInfo(String name, String time, String zone, double longitude, double latitude) {
        this.name = name;
        this.zone = zone;
        this.time = setTime(time);
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

    private String setTime(String time) {
        int index = time.indexOf(':');
        int timeOfDay = Integer.parseInt(time.substring(0, index));

        StringBuilder timeBuilder = new StringBuilder();

        try {
            DateFormat sdf = new SimpleDateFormat("hh:mm");
            Date date = sdf.parse(time);
            timeBuilder.append(sdf.format(date)).toString();
        } catch (Exception e) {
            timeBuilder.append(time);
        }

        if (timeOfDay < 12 || timeOfDay >= 24) {
            timeBuilder.append(" am");
        } else {
            timeBuilder.append(" pm");
        }

        return timeBuilder.toString();
    }
}
