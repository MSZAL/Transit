package csc472.depaul.edu.transit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BusFragment extends Fragment {

    private BusFragmentListener listener;
    private Button busSearchButton;

    public interface BusFragmentListener {
        void onBusSubmit(String s);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_fragment, container, false);

        busSearchButton = v.findViewById(R.id.bus_search_button);

        busSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBusSubmit("Example output");
            }
        });

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
}
