package csc472.depaul.edu.transit.Bus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusDirectionFragmentAdapter extends RecyclerView.Adapter<BusDirectionFragmentAdapter.BusDirectionViewHolder> {

    private ArrayList<String> busDirections;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BusDirectionViewHolder extends RecyclerView.ViewHolder {

        TextView busDirection;

        public BusDirectionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            busDirection = itemView.findViewById(R.id.bus_direction);

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

    public BusDirectionFragmentAdapter(ArrayList<String> busDirections) {
        this.busDirections = busDirections;
    }

    @NonNull
    @Override
    public BusDirectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_direction_item,viewGroup,false);
        BusDirectionViewHolder bdvh = new BusDirectionViewHolder(v,listener);
        return bdvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BusDirectionViewHolder busDirectionViewHolder, int i) {
        String direction = busDirections.get(i);
        busDirectionViewHolder.busDirection.setText(direction);
    }

    @Override
    public int getItemCount() {
        return busDirections.size();
    }
}
