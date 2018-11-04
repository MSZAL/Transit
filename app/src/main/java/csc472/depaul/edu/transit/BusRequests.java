package csc472.depaul.edu.transit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

public class BusRequests extends HttpURLConnection{

    private static final String BASE_URL = "http://www.ctabustracker.com/bustime/api/v2/";
    private final static String CTA_API_KEY = "SRRXJ6znU8wkHiYimz6FR84ng";

    private static URL URL;
    private static HttpURLConnection CONN;


    public BusRequests(){
        super(URL);
    }


    private void setUrl(URL newRequest){
        this.URL = newRequest;
    }

    // Makes an HTTP request and returns all Bus Routes available
    // Has not been test yet!
    private CTABusInterface getAllRoutes() {
        CTABusInterface result = new BusRoute();
        BufferedReader in = null;
        try {
            this.setUrl(new URL(this.BASE_URL + "getroutes" + "?key=" + CTA_API_KEY));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("<rt>")){
                    String routeId = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                    if ((line = in.readLine()) != null){
                        String routeName = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                        BusRoute route = new BusRoute(routeName, routeId);
                        ((BusRoute) result).addRoute(route);
                    }
                }
            }
        } catch (IOException io) {
            Log.e("IOException", io.getMessage());
        }finally{
            if (in != null){
                try{
                    in.close();
                }catch(IOException io){
                    Log.e("IOException", io.getMessage());
                }
            }
        }
        return result;
    }



    // Takes a route name and a route, and returns a BusRoute that matches
    private CTABusInterface findRoute(String routeName, CTABusInterface route) {
        CTABusInterface result = null;
        if (route instanceof  BusRoute) {
            result = ((BusRoute) route).find(routeName);
        }
        return result;
    }

    // Takes a route and returns the directions available for that route
    private void findDirections(CTABusInterface route){
        BufferedReader in = null;
        try{
            this.setUrl(new URL(this.BASE_URL + "getdirections" + "?key=" + CTA_API_KEY + "&rt=" + route.getId()));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                if (line.contains("dir")){
                    line = line.replaceAll("\\[^>]*>", "").replaceAll("\t","");
                    ((BusRoute)route).addDirections(line);
                }
            }

        }catch (MalformedURLException mue){

        }catch (IOException ie){

        }finally{

        }
    }


    // Takes a route and makes an HTTP request and returns a populated route with BusStops
    private CTABusInterface getAllStops(String direction, CTABusInterface route) {
        BufferedReader in = null;
        try{
            this.setUrl(new URL(this.BASE_URL + "getstops" + "?key=" + CTA_API_KEY + "&rt=" + route.getId() + "&dir=" + direction));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("<stpid>")) {
                    String stopId = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                    String stopName = line = in.readLine().replaceAll("\\<[^>]*>", "").replace("&amp;", "&").replaceAll("\t","");
                    double longitude = Double.parseDouble(line = in.readLine().replaceAll("\\<[^>]*>", "").replace("\t", ""));
                    double latitude = Double.parseDouble(line = in.readLine().replaceAll("\\<[^>]*>","").replace("\t", ""));
                    BusStop stop = new BusStop(stopName, stopId, longitude, latitude);
                    if (route instanceof BusRoute){
                        ((BusRoute) route).addBusStop(stop);
                    }
                }
            }
        }catch (IOException io){
            Log.e("IOException", io.getMessage());
        }finally{
            if (in != null){
                try {
                    in.close();
                }catch(IOException io){
                    Log.e("IOException", io.getMessage());
                }
            }
        }
        return route;
    }


    // Takes a stop name and a route, and returns a BusStop that matches
    private CTABusInterface findStop(String stopName, CTABusInterface route) {
        CTABusInterface result = null;
        if (route instanceof BusRoute){
            result = ((BusRoute) route).find(stopName);
        }
        return result;
    }

    private ArrayList<BusPrediction> getPrediction(CTABusInterface route, CTABusInterface currentStop, CTABusInterface destinationStop){
        ArrayList<BusPrediction> resultArray = new ArrayList<>();
        BufferedReader in = null;
        try{
            this.setUrl(new URL(this.BASE_URL + "getpredictions" + "?key=" + CTA_API_KEY + "&rt=" + route.getId() + "&stpid=" + currentStop.getId() + "," + destinationStop.getId()));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            int count = 0;
            while ((line = in.readLine()) != null && resultArray.size() < 2){
                String stopName = "";
                String predictedArrivalTime = "";
                String predictedBusArrivalTime = "";
                String delay = "";
                if (line.contains("<stpnm>")){
                    stopName = line.replaceAll("\\[^>]*>", "").replaceAll("\t", "");
                }
                else if (line.contains("<prdtm>")){
                    predictedArrivalTime = line.replaceAll("\\[^>]*>", "").replaceAll("\t", "");
                    delay = (line = in.readLine().replaceAll("\\[^>]*>", "").replaceAll("\t", ""));
                }
                else if (line.contains("<prdctdn>")){
                    predictedBusArrivalTime = line.replaceAll("\\[^>]*>", "").replaceAll("\t", "");
                    BusPrediction predict = new BusPrediction(stopName, predictedArrivalTime, predictedBusArrivalTime, delay);
                    resultArray.add(predict);
                }
            }


        }catch(MalformedURLException mue){
            Log.e("MalformedURL", mue.getMessage());
        }catch(IOException io){
            Log.e("IOException", io.getMessage());
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
            }catch (IOException io){
                Log.e("IOException", io.getMessage());
            }
        }
        return resultArray;
    }

    @Override
    public void disconnect() {
        CONN.disconnect();
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
        CONN = (HttpURLConnection) URL.openConnection();
        CONN.setRequestMethod("GET");
    }


    public void getResponse(String[] params){
        String Route = params[0];
        String Direction = params[1];
        String CurrentStop = params[2];
        String Destination = params[3];

        CTABusInterface allRoutes = this.getAllRoutes();
        Log.i("AllRoutes", "" + allRoutes.getDescription());
        CTABusInterface specifiedRoute = this.findRoute(Route, allRoutes);
        Log.i("FindRoute", "Returned name: " + specifiedRoute.getName() + ", Request Name: " + Route);
        this.findDirections(specifiedRoute);
        Log.i("Directions", "" + ((BusRoute)specifiedRoute).getDirections().toString());
        CTABusInterface allStops = this.getAllStops(Direction, specifiedRoute);
        Log.i("GetAllStops", allStops.getDescription());
        CTABusInterface currentStop = this.findStop(CurrentStop, allStops);
        CTABusInterface destinationStop = this.findStop(Destination, allStops);
        Log.i("CurrentStop", currentStop.getName());
        Log.i("DestinationStop", destinationStop.getName());
        ArrayList<BusPrediction> predictArray = this.getPrediction(specifiedRoute, currentStop, destinationStop);
        Log.i("GetPredictions", predictArray.toString());

    }
}
