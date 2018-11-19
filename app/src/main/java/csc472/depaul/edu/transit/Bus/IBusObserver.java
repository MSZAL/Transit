package csc472.depaul.edu.transit.Bus;

import java.util.ArrayList;

import csc472.depaul.edu.transit.Bus.BusRoute;

public interface IBusObserver {
    void update(ArrayList<BusRoute> busRoutes);
}
