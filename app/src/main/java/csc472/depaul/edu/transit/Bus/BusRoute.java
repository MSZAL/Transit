package csc472.depaul.edu.transit.Bus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BusRoute implements Parcelable {

    private String name;
    private String id;

    private ArrayList<String> directions = new ArrayList<>();

    public BusRoute(String name, String id){
        this.name = name;
        this.id = id;
    }

    public BusRoute(){ }

    protected BusRoute(Parcel in) {
        name = in.readString();
        id = in.readString();
        directions = in.createStringArrayList();
    }

    public static final Creator<BusRoute> CREATOR = new Creator<BusRoute>() {
        @Override
        public BusRoute createFromParcel(Parcel in) {
            return new BusRoute(in);
        }

        @Override
        public BusRoute[] newArray(int size) {
            return new BusRoute[size];
        }
    };

    public void setName(String newName) {
        this.name = newName;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void addDirections(String directions) {
        this.directions.add(directions);
    }

    public void clearDirections() { this.directions = new ArrayList<>(); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeStringList(directions);
    }
}
