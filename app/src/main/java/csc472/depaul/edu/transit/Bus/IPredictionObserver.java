package csc472.depaul.edu.transit.Bus;

import android.gesture.Prediction;

import java.util.ArrayList;

public interface IPredictionObserver {
    void update(ArrayList<BusPrediction> busPredictions);
    BusRoute getRoute();
    BusStop getStop();
}
