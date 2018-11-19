package csc472.depaul.edu.transit.Bus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusFragmentAdapter extends RecyclerView.Adapter<BusFragmentAdapter.BusViewHolder> {

    private ArrayList<BusRoute> busRoutes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {

        TextView busId;
        TextView busName;

        public BusViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            busId = itemView.findViewById(R.id.bus_id);
            busName = itemView.findViewById(R.id.bus_name);

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

    public BusFragmentAdapter(ArrayList<BusRoute> busRoutes) {
        this.busRoutes = busRoutes;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_item,viewGroup,false);
        BusViewHolder bvh = new BusViewHolder(v,listener);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder busViewHolder, int i) {
        BusRoute currentRoute = busRoutes.get(i);

        busViewHolder.busId.setText(currentRoute.getId());
        busViewHolder.busName.setText(currentRoute.getName());
    }

    @Override
    public int getItemCount() {
        return busRoutes.size();
    }
}
