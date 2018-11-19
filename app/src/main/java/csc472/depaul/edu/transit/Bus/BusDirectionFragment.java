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

public class BusDirectionFragment extends Fragment implements IDirectionObserver {

    private BusDirectionFragmentListener listener;
    private BusRoute busRoute;
    private RecyclerView recyclerView;
    private BusDirectionFragmentAdapter busDirectionFragmentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    public interface BusDirectionFragmentListener {
        void onDirectionClick(BusRoute busRoute,String direction);
    }

    // Use a factory method rather than passing bundle from main activity
    public static BusDirectionFragment newInstance(BusRoute newBusRoute) {
        Bundle args = new Bundle();
        args.putParcelable("bus_route", newBusRoute);
        // args.putString("string", s);
        BusDirectionFragment fragment = new BusDirectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_direction_fragment, container, false);
        view = v;
        //textView = v.findViewById(R.id.bus_result_text);

        if (getArguments() != null) {
            busRoute = getArguments().getParcelable("bus_route");
            displayDirections();

        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BusDirectionFragmentListener) {
            listener = (BusDirectionFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement BusDirectionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void displayDirections() {
        DirectionRequestThread directionRequestThread = DirectionRequestThread.getDirectionRequestThread();
        directionRequestThread.addObserver(this);
        Thread t = new Thread(directionRequestThread);
        t.start();
    }

    @Override
    public BusRoute getRoute() {
        return busRoute;
    }

    public void update(ArrayList<String> busDirections){

        Message message = Message.obtain();
        if (message != null) {
            message.obj = busDirections;

            mainThreadHandler.sendMessage(message);
        }

    }

    private Handler mainThreadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                @SuppressWarnings("unchecked") final ArrayList<String> busDirections = (ArrayList<String>) msg.obj;


                recyclerView = view.findViewById(R.id.bus_direction_fragment_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                busDirectionFragmentAdapter = new BusDirectionFragmentAdapter(busDirections);
                recyclerView.setAdapter(busDirectionFragmentAdapter);

                busDirectionFragmentAdapter.setOnItemClickListener(new BusDirectionFragmentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        listener.onDirectionClick(busRoute,busDirections.get(position));
                    }
                });
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
