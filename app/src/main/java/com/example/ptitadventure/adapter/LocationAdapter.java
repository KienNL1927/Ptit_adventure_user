package com.example.ptitadventure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.model.CheckPoint;
import com.example.ptitadventure.model.Location;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> locations;
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    public void setOnLocationClickListener(OnLocationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkpoint, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        ImageView locationImage;
        TextView locationName;
        TextView locationDescription;
        LinearProgressIndicator progressSubtasks;
        TextView textProgress;
        Chip chipStatus;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationImage = itemView.findViewById(R.id.image_location);
            locationName = itemView.findViewById(R.id.text_location_name);
            locationDescription = itemView.findViewById(R.id.text_location_description);
            progressSubtasks = itemView.findViewById(R.id.progress_subtasks);
            textProgress = itemView.findViewById(R.id.text_progress);
            chipStatus = itemView.findViewById(R.id.chip_location_status);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onLocationClick(locations.get(position));
                }
            });
        }

        void bind(Location location) {
            locationName.setText(location.getName());
            locationDescription.setText("Tầng: " + location.getFloors());

            int completedSubtasks = location.getCompletedSubtasks();
            int totalSubtasks = location.getTotalSubtasks();

            progressSubtasks.setMax(totalSubtasks);
            progressSubtasks.setProgress(completedSubtasks);

            textProgress.setText(completedSubtasks + "/" + totalSubtasks + " subtasks completed");

            if (completedSubtasks == totalSubtasks) {
                chipStatus.setText("Đã hoàn thành");
                chipStatus.setChipBackgroundColorResource(R.color.completed_color);
            } else {
                chipStatus.setText("Chưa hoàn thành");
                chipStatus.setChipBackgroundColorResource(R.color.not_completed_color);
            }
        }
    }
}
