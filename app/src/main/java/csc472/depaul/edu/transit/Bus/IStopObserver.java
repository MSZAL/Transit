package csc472.depaul.edu.transit.Bus;

import java.util.ArrayList;

import csc472.depaul.edu.transit.Bus.BusRoute;
import csc472.depaul.edu.transit.Bus.BusStop;

public interface IStopObserver {
    void update(ArrayList<BusStop> busStops);
    BusRoute getRoute();
    String getDirection();
}
