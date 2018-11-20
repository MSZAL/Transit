package csc472.depaul.edu.transit.Bus;

import android.util.Log;

import java.util.ArrayList;

public class PredictionRequestThread implements Runnable {

    private static PredictionRequestThread predictionRequestThread;
    private ArrayList<IPredictionObserver> iPredictionObservers = new ArrayList<>();

    static {
        predictionRequestThread = new PredictionRequestThread();
    }

    private PredictionRequestThread() { }

    public static PredictionRequestThread getPredictionRequestThread() { return predictionRequestThread; }

    public void addObserver(IPredictionObserver iPredictionObserver) {
        iPredictionObservers.add(iPredictionObserver);
    }

    @Override
    public void run() {
        // Get request
        try {
            BusRequests busRequests = new BusRequests();

            // Tell fragment to update
            if (iPredictionObservers != null) {
                for (IPredictionObserver observer : iPredictionObservers) {
                    BusRoute busRoute = observer.getRoute();
                    BusStop busStop = observer.getStop();
                    ArrayList<BusPrediction> busPredictions = busRequests.getPrediction(busRoute, busStop);

                    for (BusPrediction busPrediction : busPredictions) {
                        busRequests.getVehicleDest(busPrediction,busRoute);
                    }

                    observer.update(busPredictions);
                }
            }
        }
        catch (Exception e) {
            Log.e("REQUEST ERROR: ", e.getMessage());
        }
    }
}
