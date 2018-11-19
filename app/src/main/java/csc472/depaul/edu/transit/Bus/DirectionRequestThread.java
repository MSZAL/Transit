package csc472.depaul.edu.transit.Bus;

import android.util.Log;

import java.util.ArrayList;

public class DirectionRequestThread implements Runnable {

    private static DirectionRequestThread directionRequestThread;
    private ArrayList<IDirectionObserver> iDirectionObservers = new ArrayList<>();

    static {
        directionRequestThread = new DirectionRequestThread();
    }

    private DirectionRequestThread() { }

    public static DirectionRequestThread getDirectionRequestThread() { return directionRequestThread; }

    public void addObserver(IDirectionObserver iDirectionObserver) {
        iDirectionObservers.add(iDirectionObserver);
    }

    @Override
    public void run() {

        try {
            BusRequests busRequests = new BusRequests();

            for (IDirectionObserver iDirectionObserver: iDirectionObservers) {
                BusRoute route = iDirectionObserver.getRoute();

                // Will modify BusRoute object and append the directions to it
                busRequests.findDirections(route);
                ArrayList<String> directions = route.getDirections();
                iDirectionObserver.update(directions);
            }
        }
        catch (Exception e) {
            Log.e("EXTREME: ", e.getMessage());
        }
    }
}
