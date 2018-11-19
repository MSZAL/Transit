package csc472.depaul.edu.transit.Bus;

public class BusPrediction {

    private String stopName;
    private String predictedArrivalTime;
    private String predictedBusArrivalTime;
    private String delay;

    public BusPrediction(String stopName, String predictedArrivalTime, String predictedBusArrivalTime, String delay){
        this.stopName = stopName;
        this.predictedArrivalTime = predictedArrivalTime;
        this.predictedBusArrivalTime = predictedBusArrivalTime;
        this.delay = delay;
    }

    public BusPrediction(){ }

    public void setStopName(String stopName){
        this.stopName = stopName;
    }

    public void setPredictedArrivalTime(String predictedArrivalTime){
        this.predictedArrivalTime = predictedArrivalTime;
    }

    public void setPredictedBusArrivalTime(String predictedBusArrivalTime){
        this.predictedBusArrivalTime = predictedBusArrivalTime;
    }

    public void setDelay(String delay){
        this.delay = delay;
    }


    public String getStopName(){
        return this.stopName;
    }

    public String getPredictedArrivalTime(){
        return this.predictedArrivalTime;
    }

    public String getPredictedBusArrivalTime(){
        return this.predictedBusArrivalTime;
    }

    public String getDelay(){
        return this.delay;
    }

    @Override
    public String toString(){
        return ("Bus Stop Name: " + this.stopName + ", Predicted Arrival Time: " + this.predictedArrivalTime + ", Predicted Bus Arrival Time: " + this.predictedBusArrivalTime + ", is there a delay: " + this.delay);
    }

}
