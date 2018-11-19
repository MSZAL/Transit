package csc472.depaul.edu.transit.Bus;

import android.util.Log;

import java.util.ArrayList;

public class BusRequestThread implements Runnable {

    private static BusRequestThread busRequestThread;
    private ArrayList<IBusObserver> iBusObservers = new ArrayList<>();

    static {
        busRequestThread = new BusRequestThread();
    }

    private BusRequestThread() { }

    public static BusRequestThread getBusRequestThread() { return busRequestThread; }

    public void addObserver(IBusObserver iBusObserver) {
        iBusObservers.add(iBusObserver);
    }

    @Override
    public void run() {
        // Get request
        try {
            BusRequests busRequests = new BusRequests();
            ArrayList<BusRoute> result = busRequests.getAllRoutes();

            // Tell fragment to update
            if (iBusObservers != null) {
                for (IBusObserver observer : iBusObservers) {
                    observer.update(result);
                }
            }
        }
        catch (Exception e) {
            Log.e("EXTREME: ", e.getMessage());
        }
    }
}
