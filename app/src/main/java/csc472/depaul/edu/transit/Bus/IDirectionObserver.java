package csc472.depaul.edu.transit.Bus;

import java.util.ArrayList;

import csc472.depaul.edu.transit.Bus.BusRoute;

public interface IDirectionObserver {
    void update(ArrayList<String> directions);
    BusRoute getRoute();
}
