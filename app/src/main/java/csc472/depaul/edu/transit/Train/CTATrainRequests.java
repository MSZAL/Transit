package csc472.depaul.edu.transit.Train;


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
    private String baseUrl = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=";

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


    private CTATrainPrediction getStopPrediction(int Stopid){
        CTATrainPrediction predict = new CTATrainPrediction();
        BufferedReader in = null;
        try {
            this.setUrl(new URL(baseUrl + CTA_TRAIN_API_KEY + "&" + "stpid=" + Stopid + "&max=1"));
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

    private ArrayList<CTATrainPrediction> followTrain(int runNumber, String destinationStop){

        return null;
    }


    public void ServeRequest(int Stopid){

        CTATrainPrediction predict = this.getStopPrediction(Stopid);

        Log.i("PredictServed", predict.toString());
    }



}
