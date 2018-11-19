package csc472.depaul.edu.transit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BusStopsFragment extends Fragment {

    BusRoute busRoute;

    // Use a factory method rather than passing bundle from main activity
    public static BusStopsFragment newInstance(BusRoute newBusRoute) {
        Bundle args = new Bundle();
        args.putParcelable("bus_route", newBusRoute);
        // args.putString("string", s);
        BusStopsFragment fragment = new BusStopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_fragment_result, container, false);

        //textView = v.findViewById(R.id.bus_result_text);

        if (getArguments() != null) {
            busRoute = getArguments().getParcelable("bus_route");
            displayStops();

        }

        return v;
    }

    private void displayStops() {
        //BusRequestThread busRequestThread = BusRequestThread.getBusRequestThread();
        //busRequestThread.addObserver(this);
        //Thread t = new Thread(busRequestThread);
        //t.start();
    }

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

//                recyclerView = view.findViewById(R.id.bus_fragment_recycler_view);
//                recyclerView.setHasFixedSize(true);
//                Context c = view.getContext();
//                layoutManager = new LinearLayoutManager(getActivity());
//                recyclerView.setLayoutManager(layoutManager);
//                adapter = new BusFragmentAdapter(busRoutes);
//                recyclerView.setAdapter(adapter);
//
//                // When user clicks on an item in the RecyclerView
//                adapter.setOnItemClickListener(new BusFragmentAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        BusRoute busRoute = busRoutes.get(position);
//                        Log.d("BUS ROUTE: ", busRoute.getName());
//                        listener.onBusClick(busRoute);
//                    }
//                });
            }

            return true;
        }
    });
}
