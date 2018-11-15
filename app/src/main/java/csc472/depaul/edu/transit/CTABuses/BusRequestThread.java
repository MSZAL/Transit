package csc472.depaul.edu.transit.CTABuses;

import android.util.Log;

import java.util.Stack;

import csc472.depaul.edu.transit.CTABuses.BusRequests;

public class BusRequestThread implements Runnable {


    private Stack<String[]> Requests = new Stack<>();
    private BusRequests requestor;


    public BusRequestThread(){
        requestor = new BusRequests();
    }

    public void request(String[] request){
        Requests.push(request);
    }


    @Override
    public void run() {
        try{
            while(true) {
                if (!Requests.empty()) {
                    String[] request = Requests.pop();
                    requestor.getResponse(request);
                }
            }
        }catch (Exception e){
            Log.e("EXTREME", e.getMessage());
        }
    }
}
