package csc472.depaul.edu.transit.CTABuses;

public class BusStop implements CTABusInterface {

    private String name;
    private String id;
    private double longitude;
    private double latitude;

    public BusStop(String name, String id, double longitude, double latitude){
        this.name = name;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BusStop(String name, String id){
        this.name = name;
        this.id = id;
    }

    public BusStop () { }

    @Override
    public void setName(String newName){
        this.name = newName;
    }

    @Override
    public void setId(String newId){
        this.id = newId;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId(){
        return this.id;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    @Override
    public String getDescription() {
        return ("Bus Route ID: " + this.id + ". Route Name: " + this.name);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof BusStop)){
            return false;
        }
        BusStop temp = (BusStop) obj;

        return (temp.name != this.name || temp.id != this.id);
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.id.hashCode();
        return result;
    }
}
