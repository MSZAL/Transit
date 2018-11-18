package csc472.depaul.edu.transit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BusFragmentAdapter extends RecyclerView.Adapter<BusFragmentAdapter.BusViewHolder> {

    private ArrayList<BusRoute> busRoutes;

    public static class BusViewHolder extends RecyclerView.ViewHolder {

        TextView busId;
        TextView busName;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busId = itemView.findViewById(R.id.bus_id);
            busName = itemView.findViewById(R.id.bus_name);
        }
    }

    public BusFragmentAdapter(ArrayList<BusRoute> busRoutes) {
        this.busRoutes = busRoutes;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_item,viewGroup,false);
        BusViewHolder bvh = new BusViewHolder(v);
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
