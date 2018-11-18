package csc472.depaul.edu.transit;

public class BusRoute {

    private String name;
    private String id;

    public BusRoute(String name, String id){
        this.name = name;
        this.id = id;
    }

    public BusRoute(){ }

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
}
