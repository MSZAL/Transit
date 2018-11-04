package csc472.depaul.edu.transit;

import android.util.Log;

import java.net.URL;
import java.util.Stack;

public class BusRequestThread implements Runnable {

    BusResponseSingleton storage = BusResponseSingleton.getInstance();

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
