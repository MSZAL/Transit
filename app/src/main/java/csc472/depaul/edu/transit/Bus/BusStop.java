package csc472.depaul.edu.transit.Bus;

import android.os.Parcel;
import android.os.Parcelable;

public class BusStop implements Parcelable {

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

    protected BusStop(Parcel in) {
        name = in.readString();
        id = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<BusStop> CREATOR = new Creator<BusStop>() {
        @Override
        public BusStop createFromParcel(Parcel in) {
            return new BusStop(in);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }
}
