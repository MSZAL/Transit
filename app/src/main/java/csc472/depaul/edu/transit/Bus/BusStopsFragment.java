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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusStopsFragment extends Fragment implements IStopObserver {

    private BusStopsFragmentListener listener;
    private BusRoute busRoute;
    private String direction;
    private RecyclerView recyclerView;
    private BusStopsFragmentAdapter busStopsFragmentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    public interface BusStopsFragmentListener {
        void onStopClick(BusRoute busRoute, BusStop busStop);
    }

    // Use a factory method rather than passing bundle from main activity
    public static BusStopsFragment newInstance(BusRoute newBusRoute, String direction) {
        Bundle args = new Bundle();
        args.putParcelable("bus_route", newBusRoute);
        args.putString("direction", direction);
        // args.putString("string", s);
        BusStopsFragment fragment = new BusStopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_stops_fragment, container, false);
        view = v;
        //textView = v.findViewById(R.id.bus_result_text);

        if (getArguments() != null) {
            busRoute = getArguments().getParcelable("bus_route");
            direction = getArguments().getString("direction");
            displayStops();
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BusStopsFragmentListener) {
            listener = (BusStopsFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement BusStopsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void displayStops() {
        StopRequestThread stopRequestThread = StopRequestThread.getStopRequestThread();
        stopRequestThread.addObserver(this);
        Thread t = new Thread(stopRequestThread);
        t.start();
    }

    @Override
    public BusRoute getRoute() {
        return busRoute;
    }

    @Override
    public String getDirection() { return direction; }

    public void update(ArrayList<BusStop> busStops){

        Message message = Message.obtain();
        if (message != null) {
            message.obj = busStops;

            mainThreadHandler.sendMessage(message);
        }

    }

    private Handler mainThreadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                @SuppressWarnings("unchecked") final ArrayList<BusStop> busStops = (ArrayList<BusStop>) msg.obj;


                recyclerView = view.findViewById(R.id.bus_stops_fragment_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                busStopsFragmentAdapter = new BusStopsFragmentAdapter(busStops);
                recyclerView.setAdapter(busStopsFragmentAdapter);
//
                // When user clicks on an item in the RecyclerView
                busStopsFragmentAdapter.setOnItemClickListener(new BusStopsFragmentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BusStop busStop = busStops.get(position);
                        Log.d("BUS ROUTE: ", busRoute.getName());
                        listener.onStopClick(busRoute, busStop);
                    }
                });
            }

            return true;
        }
    });
}
