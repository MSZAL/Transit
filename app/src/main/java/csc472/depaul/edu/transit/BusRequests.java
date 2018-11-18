package csc472.depaul.edu.transit;

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
