package csc472.depaul.edu.transit.CTABuses;

import java.util.ArrayList;
import java.util.List;

public class BusRoute implements CTABusInterface {

    private String name;
    private String id;
    private ArrayList<String> directions = new ArrayList<>();

    private List<CTABusInterface> CTA = new ArrayList<>();

    public BusRoute (String name, String id){
        this.name = name;
        this.id = id;
    }

    public BusRoute (){ }


    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public void setId(String newId) {
        this.id = newId;
    }

    public void addDirections(String direction){
        this.directions.add(direction);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getDirections(String direction){
        for (String dir : directions){
            if (dir.equals(direction)){
                return dir;
            }
        }
        return null;
    }

    public ArrayList<String> getDirections(){
        return this.directions;
    }

    @Override
    public String getDescription() {
        String Desc = "Bus Route ID: " + this.id + ". Route Name: " + this.name;
        for (CTABusInterface Stop : CTA){
            Desc += "\n";
            Desc += Stop.getName();
        }
        return Desc;
    }

    // Adds a BusStop to the array of a Route object
    public void addBusStop(BusStop stop){
        CTA.add(stop);
    }

    // Adds a Route to the array of a Route object
    public void addRoute(BusRoute route) {
        CTA.add(route);
    }


    // Searches for a BusStop object within the array
    public boolean contains(BusStop stop){
        for (CTABusInterface Stop : CTA){
            if (Stop.equals(stop)){
                return true;
            }
        }
        return false;
    }

    // Searches for a name within the array
    public boolean contains(String name){
        for (CTABusInterface Stop : CTA){
            if (Stop.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    // Takes a CTA BusStop object, and returns a CTA BusStop object that matches
    public CTABusInterface find(BusStop stop){
        CTABusInterface result = null;
        if (this.contains(stop)){
            for (CTABusInterface Stop : CTA){
                if (Stop.equals(stop)){
                    result = Stop;
                }
            }
        }
        return result;
    }

    // Takes in the name of a Stop or Route, and returns a CTA object that matches
    public CTABusInterface find(String name){
        CTABusInterface result = null;
        if (this.contains(name)){
            for (CTABusInterface Stop : CTA){
                if (Stop.getName().equals(name)){
                    result = Stop;
                }
            }
        }
        return result;
    }

    public int size(){
        return this.CTA.size();
    }

}
