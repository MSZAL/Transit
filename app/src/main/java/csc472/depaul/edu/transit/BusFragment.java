package csc472.depaul.edu.transit;

import android.content.Context;
import android.net.sip.SipSession;
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
import android.widget.Button;

import java.util.ArrayList;

public class BusFragment extends Fragment implements IBusObserver {

    private BusFragmentListener listener;
    //private Button busSearchButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    public interface BusFragmentListener {
        void onBusSubmit(String s);
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
                @SuppressWarnings("unchecked")
                ArrayList<BusRoute> busRoutes = (ArrayList<BusRoute>) msg.obj;

                recyclerView = view.findViewById(R.id.bus_fragment_recycler_view);
                recyclerView.setHasFixedSize(true);
                Context c = view.getContext();
                layoutManager = new LinearLayoutManager(getActivity());
                Log.d("APPLICATION: ", "STILL WORKING");
                recyclerView.setLayoutManager(layoutManager);
                adapter = new BusFragmentAdapter(busRoutes);
                recyclerView.setAdapter(adapter);
            }

            return true;
        }
    });
}
