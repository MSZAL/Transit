package csc472.depaul.edu.transit.Bus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import csc472.depaul.edu.transit.R;

public class BusPredictionsAdapter extends RecyclerView.Adapter<BusPredictionsAdapter.BusPredictionViewHolder> {

    private ArrayList<BusPrediction> busPredictions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BusPredictionViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView vehicleRt;
        TextView vehicleDest;

        public BusPredictionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            time = itemView.findViewById(R.id.bus_prediction_time);
            vehicleRt = itemView.findViewById(R.id.bus_prediction_rt);
            vehicleDest = itemView.findViewById(R.id.bus_prediction_dest);


//            busId = itemView.findViewById(R.id.bus_id);
//            busName = itemView.findViewById(R.id.bus_name);

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

    public BusPredictionsAdapter(ArrayList<BusPrediction> busPredictions) {
        this.busPredictions = busPredictions;
    }

    @NonNull
    @Override
    public BusPredictionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prediction_item,viewGroup,false);
        BusPredictionViewHolder bpvh = new BusPredictionViewHolder(v,listener);
        return bpvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BusPredictionViewHolder busPredictionViewHolder, int i) {
        BusPrediction currentPrediction = busPredictions.get(i);

        busPredictionViewHolder.time.setText(currentPrediction.getPredictedArrivalTime());
        busPredictionViewHolder.vehicleRt.setText("#" +currentPrediction.getVehicleRt());
        busPredictionViewHolder.vehicleDest.setText("(" + currentPrediction.getVehicleDest() + ")");


//        busViewHolder.busId.setText(currentRoute.getId());
//        busViewHolder.busName.setText(currentRoute.getName());
    }

    @Override
    public int getItemCount() {
        return busPredictions.size();
    }
}
