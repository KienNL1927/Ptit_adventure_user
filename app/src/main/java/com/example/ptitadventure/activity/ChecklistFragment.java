package com.example.ptitadventure.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.model.CheckPoint;
import com.example.ptitadventure.model.Subtask;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChecklistFragment extends Fragment {

    private TabLayout tabLayout;
    private RecyclerView recyclerViewCheckpoints;
    private CheckpointAdapter checkpointAdapter;
    private List<CheckPoint> incompleteCheckpoints;
    private List<CheckPoint> completedCheckpoints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklist, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        recyclerViewCheckpoints = view.findViewById(R.id.recycler_view_tasks);

        incompleteCheckpoints = new ArrayList<>();
        completedCheckpoints = new ArrayList<>();

        Subtask subtask1 = new Subtask("ST1", "Tìm cửa", true, 2);
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);
        // Initialize with some dummy data
        incompleteCheckpoints.add(new CheckPoint("CP1", "Tòa nhà A1", "Khám phá tòa nhà A1", 5, subtasks, null, 0, false));
        incompleteCheckpoints.add(new CheckPoint("CP2", "Tòa nhà A2", "Thử thách toán học", 4, subtasks, null, 0, false));
        completedCheckpoints.add(new CheckPoint("CP3", "Thư viện", "Tìm sách về CNTT", 1, subtasks, null, 0, false));
        completedCheckpoints.add(new CheckPoint("CP4", "Sân vận động", "Hoạt động thể thao", 1, subtasks, null, 0, false));

        checkpointAdapter = new CheckpointAdapter(incompleteCheckpoints);
        recyclerViewCheckpoints.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCheckpoints.setAdapter(checkpointAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    checkpointAdapter.updateCheckpoints(incompleteCheckpoints);
                } else {
                    checkpointAdapter.updateCheckpoints(completedCheckpoints);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    private static class CheckpointAdapter extends RecyclerView.Adapter<CheckpointAdapter.CheckpointViewHolder> {

        private List<CheckPoint> checkpoints;

        public CheckpointAdapter(List<CheckPoint> checkpoints) {
            this.checkpoints = checkpoints;
        }

        @NonNull
        @Override
        public CheckpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkpoint, parent, false);
            return new CheckpointViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CheckpointViewHolder holder, int position) {
            CheckPoint checkpoint = checkpoints.get(position);
            holder.bind(checkpoint);
        }

        @Override
        public int getItemCount() {
            return checkpoints.size();
        }

        public void updateCheckpoints(List<CheckPoint> newCheckpoints) {
            this.checkpoints = newCheckpoints;
            notifyDataSetChanged();
        }

        static class CheckpointViewHolder extends RecyclerView.ViewHolder {
            ImageView locationImage;
            TextView locationName;
            TextView locationDescription;
            LinearProgressIndicator progressSubtasks;
            TextView textProgress;
            Chip chipStatus;

            public CheckpointViewHolder(@NonNull View itemView) {
                super(itemView);
                locationImage = itemView.findViewById(R.id.image_location);
                locationName = itemView.findViewById(R.id.text_location_name);
                locationDescription = itemView.findViewById(R.id.text_location_description);
                progressSubtasks = itemView.findViewById(R.id.progress_subtasks);
                textProgress = itemView.findViewById(R.id.text_progress);
                chipStatus = itemView.findViewById(R.id.chip_location_status);
            }

            public void bind(CheckPoint checkpoint) {
                locationName.setText(checkpoint.getName());
                locationDescription.setText("Tầng: " + checkpoint.getBuildings());

                int completedSubtasks = checkpoint.getCompletedSubtasks();
                int totalSubtasks = checkpoint.getTotalSubtasks();

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
}