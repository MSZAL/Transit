package csc472.depaul.edu.transit.CTATrains;



import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CTATrainRequests extends HttpURLConnection {

    private final String CTA_TRAIN_API_KEY = "d37555ccc09141848543ab21e287b560";
    private String baseUrl = "http://lapi.transitchicago.com/api/1.0/";

    private static URL URL;
    private static HttpURLConnection CONN;

    public CTATrainRequests(){
        super(URL);
    }

    private void setUrl(URL newRequest){
        this.URL = newRequest;
    }


    @Override
    public void disconnect() {
        if (CONN != null){
            CONN.disconnect();
        }
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

    // Gets information about the current stop selected by the user
    private CTATrainPrediction getStopPrediction(int Stopid){
        CTATrainPrediction predict = new CTATrainPrediction();
        BufferedReader in = null;
        try {
            this.setUrl(new URL(baseUrl + "ttarrivals.aspx?key=" + CTA_TRAIN_API_KEY + "&" + "stpid=" + Stopid + "&max=1"));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String line;
            String stopName = "";
            String runNumber = "";
            String routeName = "";
            String timeStamp = "";
            String estimatedArrivalTime = "";
            String isApproaching = "";
            String isDelayed = "";
            String latitude = "";
            String longitude = "";
            while ((line = in.readLine()) != null){
                if (line.contains("<staNm>")){
                    line = line.replaceAll("\\<[^>]*>", ", ")
                            .replaceAll("\t","");
                    String[] temp = line.split(", ");
                    stopName = temp[13];
                    runNumber = temp[17];
                    routeName = temp[19];
                    timeStamp = temp[27];
                    estimatedArrivalTime = temp[29];
                    isApproaching = temp[31];
                    isDelayed = temp[35];
                    latitude = temp[40];
                    longitude = temp[42];
                }
            }
            predict.setStopName(stopName);
            predict.setRunNumber(runNumber);
            predict.setRouteName(routeName);
            predict.setTimeStamp(timeStamp);
            predict.setEstimatedArrivalTime(estimatedArrivalTime);
            predict.setIsApproaching(isApproaching);
            predict.setIsDelayed(isDelayed);
            predict.setLatitude(latitude);
            predict.setLongitude(longitude);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (in != null){
                try{
                    in.close();
                }catch(IOException io){
                    Log.e("IOException", io.getMessage());
                }
            }
        }

        return predict;
    }

    // Gets all the train stations in the current route selected by the user in the appropriate direction
    private ArrayList<CTATrainPrediction> getAllRouteStops(int runNumber){
        ArrayList<CTATrainPrediction> allStopsInRoute = new ArrayList<>();
        BufferedReader in;
        try{
            this.setUrl(new URL(baseUrl + "ttfollow.aspx?key=" + CTA_TRAIN_API_KEY + "&" + "runnumber=" + runNumber));
            this.connect();
            in = new BufferedReader(new InputStreamReader(CONN.getInputStream()));
            String stopName = "";
            String routeName = "";
            String timeStamp = "";
            String estimatedTimeArrival = "";
            String isApproaching = "";
            String isDelayed = "";
            String line;
            if((line = in.readLine()) != null){
                String[] ETA = line.split("<eta>");
                for (int i = 1; i< ETA.length; i++){
                    String[] replaced = ETA[i].split("\\<[^>]*>");
                    stopName = replaced[5];
                    routeName = replaced[11];
                    timeStamp = replaced[19];
                    estimatedTimeArrival = replaced[21];
                    isApproaching = replaced[23];
                    isDelayed = replaced[27];
                    CTATrainPrediction prediction = new CTATrainPrediction();
                    prediction.setStopName(stopName);
                    prediction.setRunNumber("" + runNumber);
                    prediction.setRouteName(routeName);
                    prediction.setTimeStamp(timeStamp);
                    prediction.setEstimatedArrivalTime(estimatedTimeArrival);
                    prediction.setIsApproaching(isApproaching);
                    prediction.setIsDelayed(isDelayed);
                    allStopsInRoute.add(prediction);
                }
            }
        }catch(IOException io){

        }

        return allStopsInRoute;
    }

    private CTATrainPrediction followTrain(ArrayList<CTATrainPrediction> predictions, int destinationStopId){

        return null;
    }


    // Gets the selected ID from the user and uses it to find stop and route information
    public void ServeRequest(int Stopid){

        CTATrainPrediction predict = this.getStopPrediction(Stopid);
        Log.i("PredictServed", predict.toString());
        ArrayList<CTATrainPrediction> AllStopsInRoute = this.getAllRouteStops(predict.getRunNumber());
        Log.i("AllStopsInRoute", AllStopsInRoute.toString());
    }



}
