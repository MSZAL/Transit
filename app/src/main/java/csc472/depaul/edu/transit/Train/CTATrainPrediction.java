package csc472.depaul.edu.transit.Train;

class CTATrainPrediction {

    private String stopName;
    private String runNumber;
    private String routeName;
    private String timeStamp;
    private String estimatedArrivalTime;
    private String isApproaching;
    private String isDelayed;
    private String latitude;
    private String longitude;

    public CTATrainPrediction (){

    }

    public CTATrainPrediction(String stopName, String runNumber, String routeName,
                              String timeStamp, String estimatedArrivalTime,
                              String isApproaching, String isDelayed,
                              String latitude, String longitude){
        this.stopName = stopName;
        this.runNumber = runNumber;
        this.routeName = routeName;
        this.timeStamp = timeStamp;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.isApproaching = isApproaching;
        this.isDelayed = isDelayed;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public void setStopName(String stopName){
        this.stopName = stopName;
    }

    public void setRunNumber(String runNumber){
        this.runNumber = runNumber;
    }

    public void setRouteName(String routeName){
        String result = "";
        switch (routeName){
            case "Red":
                result = "Red Line";
                break;
            case "Blue":
                result = "Blue Line";
                break;
            case "Brn":
                result = "Brown Line";
                break;
            case "G":
                result = "Green Line";
                break;
            case "Org":
                result = "Orange Line";
                break;
            case "P":
                result = "Purple Line";
                break;
            case "Pink":
                result = "Pink Line";
                break;
            case "Y":
                result = "Yellow Line";
                break;
        }

        this.routeName = result;
    }

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime){
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public void setIsApproaching(String isApproaching) {
        this.isApproaching = isApproaching;
    }

    public void setIsDelayed(String isDelayed) {
        this.isDelayed = isDelayed;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getStopName(){
        return this.stopName;
    }

    public int getRunNumber(){
        return Integer.parseInt(this.runNumber);
    }

    public String getRouteName(){
        return this.routeName;
    }

    public String getTimeStamp(){
        return this.timeStamp;
    }

    public String getEstimatedArrivalTime(){
        return this.estimatedArrivalTime;
    }

    public boolean getIsApproaching(){
        if (this.isApproaching == "1"){
            return true;
        }
        return false;
    }

    public boolean getIsDelayed(){
        if (this.isDelayed == "1"){
            return true;
        }
        return false;
    }

    public Double getLatitude(){
        return Double.parseDouble(this.latitude);
    }

    public Double getLongitude(){
        return Double.parseDouble(this.longitude);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Stop Name: ").append(this.stopName).append(", ")
                .append("Route Name: ").append(this.routeName).append(", ")
                .append("Estimated Time of Arrival: ").append(this.estimatedArrivalTime).append("\n");

        return sb.toString();
    }

}
