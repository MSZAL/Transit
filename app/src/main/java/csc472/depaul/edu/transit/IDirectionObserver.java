package csc472.depaul.edu.transit;

import java.util.ArrayList;

public interface IDirectionObserver {
    void update(ArrayList<String> directions);
    BusRoute getRoute();
}
