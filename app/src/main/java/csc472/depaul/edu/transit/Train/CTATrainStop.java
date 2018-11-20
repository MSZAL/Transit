package csc472.depaul.edu.transit.Train;

public class CTATrainStop {

    private int stopID;
    private String direction;
    private String stopName;
    private int parentID;

    public CTATrainStop(){

    }

    public CTATrainStop (int stopID, String direction, String stopName, int parentID){
        this.stopID = stopID;
        this.direction = direction;
        this.stopName = stopName;
        this.parentID = parentID;
    }

    public void setStopID(int stopID){
        this.stopID = stopID;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    public void setStopName(String stopName){
        this.stopName = stopName;
    }

    public void setParentID(int parentID){
        this.parentID = parentID;
    }

    public int getStopID(){
        return this.stopID;
    }

    public String getDirection(){
        return this.direction;
    }

    public String getStopName(){
        return this.stopName;
    }

    public int getParentID(){
        return this.parentID;
    }
}
