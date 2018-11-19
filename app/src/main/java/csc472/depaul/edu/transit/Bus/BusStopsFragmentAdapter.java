package csc472.depaul.edu.transit.Bus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusStopsFragmentAdapter extends RecyclerView.Adapter<BusStopsFragmentAdapter.BusStopsViewHolder> {

    private ArrayList<BusStop> busStops;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BusStopsViewHolder extends RecyclerView.ViewHolder {

        TextView busText;

        public BusStopsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            busText = itemView.findViewById(R.id.bus_stop_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public BusStopsFragmentAdapter(ArrayList<BusStop> busStops) {
        this.busStops = busStops;
    }

    @NonNull
    @Override
    public BusStopsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stop_item,viewGroup,false);
        BusStopsViewHolder bsvh = new BusStopsViewHolder(v,listener);
        return bsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BusStopsViewHolder busStopsViewHolder, int i) {
        String direction = busStops.get(i).getName();
        busStopsViewHolder.busText.setText(direction);
    }

    @Override
    public int getItemCount() {
        return busStops.size();
    }
}
