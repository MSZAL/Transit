package csc472.depaul.edu.transit.Bus;

import android.util.Log;

import java.util.ArrayList;

public class StopRequestThread implements Runnable {

    private static StopRequestThread stopRequestThread;
    private ArrayList<IStopObserver> iStopObservers = new ArrayList<>();

    static {
        stopRequestThread = new StopRequestThread();
    }

    private StopRequestThread() { }

    public static StopRequestThread getStopRequestThread() { return stopRequestThread; }

    public void addObserver(IStopObserver iStopObserver) {
        iStopObservers.add(iStopObserver);
    }

    @Override
    public void run() {

        try {
            BusRequests busRequests = new BusRequests();

            for (IStopObserver iStopObserver: iStopObservers) {
                BusRoute route = iStopObserver.getRoute();
                String direction = iStopObserver.getDirection();

                // Find all stops and update
                ArrayList<BusStop> busStops = busRequests.getAllStops(route, direction);
                iStopObserver.update(busStops);

            }
        }
        catch (Exception e) {
            Log.e("EXTREME: ", e.getMessage());
        }
    }
}
