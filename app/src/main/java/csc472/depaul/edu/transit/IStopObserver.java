package csc472.depaul.edu.transit;

import java.util.ArrayList;

public interface IStopObserver {
    void update(ArrayList<BusStop> busStops);
    BusRoute getRoute();
    String getDirection();
}
