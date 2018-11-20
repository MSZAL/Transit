package csc472.depaul.edu.transit.Train;

import android.util.Log;

import java.util.Stack;

public class CTATrainRequestThread implements Runnable{

    CTATrainRequests requestor = new CTATrainRequests();

    Stack<Integer> requests = new Stack<>();

    public CTATrainRequestThread(){

    }


    public void pushRequests(int Stopid){
        requests.push(Stopid);
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (!requests.empty()) {
                    requestor.ServeRequest(requests.pop());
                }
            }
        }catch (Exception e){
            Log.e("CTA Train Thread", e.getMessage());
        }
    }
}
