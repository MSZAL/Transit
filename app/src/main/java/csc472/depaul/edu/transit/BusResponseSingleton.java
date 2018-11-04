package csc472.depaul.edu.transit;

import java.util.ArrayList;
import java.util.Stack;

public class BusResponseSingleton {
    private static final BusResponseSingleton ourInstance = new BusResponseSingleton();

    private Stack<ArrayList<String>> arrayResponse = new Stack<>();
    private Stack<String> stringResponse = new Stack<>();
    private Stack<Double> timeResponse = new Stack<>();

    public static BusResponseSingleton getInstance() {
        return ourInstance;
    }

    private BusResponseSingleton() {
    }


    public void addResponse(ArrayList<String> array){

        arrayResponse.push(array);
    }

    public void addResponse(String value){
        stringResponse.push(value);
    }

    public void addResponse(Double time){

        timeResponse.push(time);
    }


    public ArrayList<String> getArrayResponse(){
        if (!arrayResponse.empty()) {
            return arrayResponse.pop();
        }
        return new ArrayList<>();
    }

    public String getStringResponse(){
        return stringResponse.pop();
    }

    public Double getTimeResponse(){
        return timeResponse.pop();
    }


}
