package csc472.depaul.edu.transit.Bus;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    public ArrayList<BusRoute> getAllRoutes() {
        ArrayList<BusRoute> result = new ArrayList<>();
        BufferedReader in = null;
        try {
            URL = new URL(this.BASE_URL + "getroutes" + "?key=" + CTA_API_KEY);
            connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("<rt>")){
                    String routeId = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                    if ((line = in.readLine()) != null){
                        String routeName = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                        BusRoute route = new BusRoute(routeName, routeId);
                        result.add(route);
                    }
                }else if (line.contains("<error>")){
                    return result;
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

    public void findDirections(BusRoute route){
        BufferedReader in = null;
        try{
            if (route.getId() != null) {
                route.clearDirections();
                this.setUrl(new URL(this.BASE_URL + "getdirections" + "?key=" + CTA_API_KEY + "&rt=" + route.getId()));
                this.connect();
                in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("dir")) {
//                        line = line.replaceAll("\\[^>]*>", "").replaceAll("\t", "");
                        line = line.replaceAll("\\[^>]*>", "").replaceAll("\t", "");
                        line = line.substring(5, line.length() - 6); // Removes <dir> </dir> tags
                        route.addDirections(line);
                    }
                }
            }
        }catch (MalformedURLException mue){

        }catch (IOException ie){

        }finally{
            disconnect();
        }
    }

    public ArrayList<BusStop> getAllStops(BusRoute route, String direction) {
        ArrayList<BusStop> busStops = new ArrayList<>();
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
                    busStops.add(stop);
                }
            }
        }catch (IOException io){
            Log.e("IOException", io.getMessage());
        }finally{
            if (in != null){
                try {
                    in.close();
                    disconnect();
                }catch(IOException io){
                    Log.e("IOException", io.getMessage());
                }
            }
        }
        return busStops;
    }

    public ArrayList<BusPrediction> getPrediction(BusRoute route, BusStop currentStop){
        ArrayList<BusPrediction> resultArray = new ArrayList<>();
        BufferedReader in = null;
        try{
            this.setUrl(new URL(this.BASE_URL + "getpredictions" + "?key=" + CTA_API_KEY + "&rt=" + route.getId() + "&stpid=" + currentStop.getId()));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            String stopName = "";
            String predictedArrivalTime = "";
            String predictedBusArrivalTime = "";
            String vehicleId = "";
            String delay = "";
            while ((line = in.readLine()) != null){
                if (line.contains("<stpnm>")){
                    stopName = line.replaceAll("\\<[^>]*>", "").replace("&amp;", "&").replaceAll("\t","");
                }
                else if (line.contains("<prdtm>")){
                    predictedArrivalTime = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                    delay = (line = in.readLine().replaceAll("\\<[^>]*>", "").replaceAll("\t", ""));
                }
                else if (line.contains("<vid>")){
                    vehicleId = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                }
                else if (line.contains("<prdctdn>")){
                    predictedBusArrivalTime = line.replaceAll("\\<[^>]*>", "").replaceAll("\t", "");
                    BusPrediction predict = new BusPrediction(stopName, predictedArrivalTime, predictedBusArrivalTime, delay, vehicleId);
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
                    disconnect();
                }
            }catch (IOException io){
                Log.e("IOException", io.getMessage());
            }
        }
        return resultArray;
    }

    public boolean getVehicleDest(BusPrediction busPrediction, BusRoute route) {
        BufferedReader in = null;
        try{
            this.setUrl(new URL(this.BASE_URL + "getvehicles" + "?key=" + CTA_API_KEY + "&rt=" + route.getId() + "&vid=" + busPrediction.getVehicleId()));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("<des>")) {
                    busPrediction.setVehicleDest(line.replaceAll("\\<[^>]*>", "").replaceAll("\t", ""));
                }
                if (line.contains("<rt>")) {
                    busPrediction.setVehicleRt(line.replaceAll("\\<[^>]*>", "").replaceAll("\t", ""));
                }
            }
        }catch (IOException io){
            Log.e("IOException", io.getMessage());
        }finally{
            if (in != null){
                try {
                    in.close();
                    disconnect();
                }catch(IOException io){
                    Log.e("IOException", io.getMessage());
                }
            }
        }
        return true;
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
}
