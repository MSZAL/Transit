package csc472.depaul.edu.transit.metra.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class MetraDatabase {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static MetraDatabase instance;

    /* Constructor */
    private MetraDatabase(Context context) {
        this.openHelper = new DatabaseOpenHelper(context, "metra.db");
    }

    /* Singleton */
    public static MetraDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new MetraDatabase(context);
        }
        return instance;
    }

    /* Returns an ArrayList of all your Metra lines */
    public ArrayList<String> getMetraLines() {

        ArrayList<String> routes = new ArrayList<>();

        openDatabase();
        String sql = new StringBuilder().append("SELECT DISTINCT routeId FROM Stations ORDER BY routeId ASC;").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            routes.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return routes;
    }

    /* Returns an ArrayList of all your stops */
    public ArrayList<String> getStops(String routeId) {

        ArrayList<String> stops = new ArrayList<>();

        openDatabase();
        String sql = new StringBuilder().append("SELECT stopName FROM Stations WHERE routeId = '")
                .append(routeId).append("' ORDER BY stopName ASC;").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String stop = cursor.getString(0);
            stops.add(stop);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return stops;
    }

    /* Returns all the metra train data */
    public ArrayList<TripInfo> getTimes(String routeId, String departure, String destination) {

        ArrayList<TripInfo> tripInfo = new ArrayList<>();

        /* Converts from stopName to stopId */
        String departureId = getStopId(departure);
        String destinationId = getStopId(destination);

        /* Gets all the trips for this route */
        ArrayList<String> trips = getTrips(routeId);

        /* Goes through all the trips and saves their data */
        for (String trip : trips) {
            int departureOrder = getOrder(trip, departureId);
            int destinationOrder = getOrder(trip, destinationId);

            if (departureOrder < destinationOrder && (departureOrder != -99 && destinationOrder != -99)) {
                tripInfo.add(getTripInfo(trip, departureOrder, destinationOrder));
            }
        }

        return tripInfo;
    }

    /* Returns an ArrayList of TripInfo */
    private TripInfo getTripInfo(String tripId, int departureOrder, int destinationOrder) {

        ArrayList<StationInfo> stations = new ArrayList<>();

        openDatabase();
        String sql = new StringBuilder().append("SELECT stopTime, stopId, stopOrder FROM StopTimes WHERE tripId = '")
                .append(tripId).append("' ORDER BY stopOrder;").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String time = cursor.getString(0);
            String id = cursor.getString(1);
            int order = cursor.getInt(2);

            if (order >= departureOrder && order <= destinationOrder) {
                stations.add(getStationInfo(id, time));
            }

            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        TripInfo tripInfo = new TripInfo(stations, tripId);

        return tripInfo;
    }

    /* Returns the StationInfo of your trip */
    private StationInfo getStationInfo(String stopId, String time) {

        StationInfo station = null;

        openDatabase();
        String sql = new StringBuilder().append("SELECT stopName, stopZone, latatude, longatude FROM Stops WHERE stopId = '")
                .append(stopId).append("';").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        String name = cursor.getString(0);
        String zone = cursor.getString(1);
        double latitude = cursor.getFloat(2);
        double longitude = cursor.getFloat(3);

        station = new StationInfo(name, time, zone, longitude, latitude);

        cursor.close();
        closeDatabase();

        return station;
    }

    /* Returns an ArrayList of dayId's for today */
    private ArrayList<String> getDayId() {

        /* Have to use the switch statement because the cleaner way requires API 26 */

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String currentDay = "";

        switch (day) {
            case Calendar.SUNDAY: currentDay = "Sunday"; break;
            case Calendar.MONDAY: currentDay = "Monday"; break;
            case Calendar.TUESDAY: currentDay = "Tuesday"; break;
            case Calendar.WEDNESDAY: currentDay = "Wednesday"; break;
            case Calendar.THURSDAY: currentDay = "Thursday"; break;
            case Calendar.FRIDAY: currentDay = "Friday"; break;
            case Calendar.SATURDAY: currentDay = "Saturday"; break;
        }

        ArrayList<String> idList = new ArrayList<>();

        openDatabase();
        String sql = new StringBuilder().append("SELECT dayId FROM Calender WHERE ")
                .append(currentDay).append(" = 1;").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            idList.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return idList;
    }

    /* Returns an ArrayList of all your trips */
    private ArrayList<String> getTrips(String routeId) {

        ArrayList<String> trips = new ArrayList<>();
        ArrayList<String> dayId = getDayId();

        StringBuilder days = new StringBuilder("(")
                .append("dayId = '").append(dayId.get(0)).append("')");

        openDatabase();
        String sql = new StringBuilder("SELECT DISTINCT tripId FROM Trips WHERE (")
                .append(days.toString()).append(" AND routeId = '").append(routeId).append("');").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String trip = cursor.getString(0);
            trips.add(trip);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return trips;
    }

    /* Gets the order of your stop */
    private int getOrder(String tripId, String stopId) {

        int order = -99;

        openDatabase();
        String sql = new StringBuilder().append("SELECT stopOrder FROM StopTimes WHERE tripId = '")
                .append(tripId).append("' AND stopID = '").append(stopId).append("';").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            order = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return order;
    }

    /* Gets the id of your stop */
    private String getStopId(String stopName) {

        openDatabase();
        String sql = new StringBuilder().append("SELECT stopId FROM Stops WHERE stopName = '")
                .append(stopName).append("';").toString();

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        String id = cursor.getString(0);
        cursor.close();
        closeDatabase();

        return id;
    }

    /* Opens database connection */
    private void openDatabase() {
        this.database = openHelper.getWritableDatabase();
    }

    /* Closes database connection */
    private void closeDatabase() {
        if (database != null) {
            this.database.close();
        }
    }

}