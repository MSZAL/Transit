package csc472.depaul.edu.transit;

import java.util.ArrayList;

public class DirectionRequestThread implements Runnable {

    private static DirectionRequestThread directionRequestThread;
    private ArrayList<IBusObserver> iBusObservers = new ArrayList<>();

    static {
        directionRequestThread = new DirectionRequestThread();
    }

    private DirectionRequestThread() { }

    public static DirectionRequestThread getDirectionRequestThread() { return directionRequestThread; }

    public void addObserver() {

    }

    @Override
    public void run() {

    }
}
