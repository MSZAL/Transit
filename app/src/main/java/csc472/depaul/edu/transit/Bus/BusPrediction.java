package csc472.depaul.edu.transit.Bus;

public class BusPrediction {

    private String stopName;
    private String predictedArrivalTime;
    private String predictedBusArrivalTime;
    private String delay;
    private String vehicleId; //Vehicle Id
    private String vehicleDest;
    private String vehicleRt;

    public BusPrediction(String stopName, String predictedArrivalTime, String predictedBusArrivalTime, String delay, String vehicleId){
        this.stopName = stopName;
        this.predictedArrivalTime = predictedArrivalTime.substring(predictedArrivalTime.length() - 5, predictedArrivalTime.length());
        this.predictedBusArrivalTime = predictedBusArrivalTime;
        this.delay = delay;
        this.vehicleId = vehicleId;
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleDest() {
        return vehicleDest;
    }

    public void setVehicleDest(String vehicleDest) {
        this.vehicleDest = vehicleDest.replaceAll(" ", "");
    }

    public String getVehicleRt() {
        return vehicleRt;
    }

    public void setVehicleRt(String vehicleRt) {
        this.vehicleRt = vehicleRt.replaceAll(" ", "");;
    }

    @Override
    public String toString(){
        return ("Bus Stop Name: " + this.stopName + ", Predicted Arrival Time: " + this.predictedArrivalTime + ", Predicted Bus Arrival Time: " + this.predictedBusArrivalTime + ", is there a delay: " + this.delay);
    }

}
