package csc472.depaul.edu.transit;

public class BusStop {

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

    public BusStop() { }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(String newId){
        this.id = newId;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public String getName() {
        return this.name;
    }

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
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof BusStop)){
            return false;
        }
        BusStop temp = (BusStop) obj;

        return (temp.name.equals(this.name) || temp.id.equals(this.id));
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.id.hashCode();
        return result;
    }
}
