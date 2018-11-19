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

public class BusFragment extends Fragment implements IBusObserver {

    private BusFragmentListener listener;
    //private Button busSearchButton;
    private RecyclerView recyclerView;
    private BusFragmentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    public interface BusFragmentListener {
        void onBusClick(BusRoute busRoutes);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_fragment, container, false);
        view = v;

        //busSearchButton = v.findViewById(R.id.bus_search_button);

//        busSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onBusSubmit("Example output");
//            }
//        });

        displayBusRoutes();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BusFragmentListener) {
            listener = (BusFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement BusFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void displayBusRoutes() {
        BusRequestThread busRequestThread = BusRequestThread.getBusRequestThread();
        busRequestThread.addObserver(this);
        Thread t = new Thread(busRequestThread);
        t.start();
    }

    public void update(ArrayList<BusRoute> busRoutes){

        Message message = Message.obtain();
        if (message != null) {
            message.obj = busRoutes;

            mainThreadHandler.sendMessage(message);
        }

    }

    private Handler mainThreadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg != null) {
                @SuppressWarnings("unchecked") final ArrayList<BusRoute> busRoutes = (ArrayList<BusRoute>) msg.obj;

                recyclerView = view.findViewById(R.id.bus_fragment_recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new BusFragmentAdapter(busRoutes);
                recyclerView.setAdapter(adapter);

                // When user clicks on an item in the RecyclerView
                adapter.setOnItemClickListener(new BusFragmentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        BusRoute busRoute = busRoutes.get(position);
                        Log.d("BUS ROUTE: ", busRoute.getName());
                        listener.onBusClick(busRoute);
                    }
                });
            }

            return true;
        }
    });
}
