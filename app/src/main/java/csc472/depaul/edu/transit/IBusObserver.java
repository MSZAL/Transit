package csc472.depaul.edu.transit;

import java.util.ArrayList;

public interface IBusObserver {
    void update(ArrayList<BusRoute> busRoutes);
}
