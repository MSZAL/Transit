package csc472.depaul.edu.transit.Bus;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusPredictionsFragment extends Fragment implements IPredictionObserver {

    private BusRoute busRoute;
    private BusStop busStop;
    private RecyclerView recyclerView;
    private BusPredictionsAdapter busPredictionsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    // Use a factory method rather than passing bundle from main activity
    public static BusPredictionsFragment newInstance(BusRoute newBusRoute, BusStop busStop) {
        Bundle args = new Bundle();
        args.putParcelable("bus_route", newBusRoute);
        args.putParcelable("bus_stop", busStop);
        BusPredictionsFragment fragment = new BusPredictionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_predictions_fragment, container, false);
        view = v;

        if (getArguments() != null) {
            busRoute = getArguments().getParcelable("bus_route");
            busStop = getArguments().getParcelable("bus_stop");
            displayPredictions();
        }

        return v;
    }

    private void displayPredictions() {
        PredictionRequestThread predictionRequestThread = PredictionRequestThread.getPredictionRequestThread();
        predictionRequestThread.addObserver(this);
        Thread t = new Thread(predictionRequestThread);
        t.start();
    }

    @Override
    public BusRoute getRoute() {
        return busRoute;
    }

    @Override
    public BusStop getStop() {
        return busStop;
    }

    public void update(ArrayList<BusPrediction> busPredictions){

        Message message = Message.obtain();
        if (message != null) {
            message.obj = busPredictions;

            mainThreadHandler.sendMessage(message);
        }

    }

    private Handler mainThreadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                @SuppressWarnings("unchecked") final ArrayList<BusPrediction> busPredictions = (ArrayList<BusPrediction>) msg.obj;

                recyclerView = view.findViewById(R.id.bus_predictions_fragment_recycler_view);
                //recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                busPredictionsAdapter = new BusPredictionsAdapter(busPredictions);
                recyclerView.setAdapter(busPredictionsAdapter);
            }

            return true;
        }
    });
}
