package csc472.depaul.edu.transit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BusFragmentResult extends Fragment {

    private TextView textView;


    // Use a factory method rather than passing bundle from main activity
    public static BusFragmentResult newInstance(String s) {
        Bundle args = new Bundle();
        args.putString("string", s);
        BusFragmentResult fragment = new BusFragmentResult();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_fragment_result, container, false);

        textView = v.findViewById(R.id.bus_result_text);

        if (getArguments() != null) {
            String text = getArguments().getString("string");
            textView.setText(text);
        }

        return v;
    }
}
