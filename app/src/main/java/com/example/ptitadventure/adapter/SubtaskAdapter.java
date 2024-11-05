package com.example.ptitadventure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ptitadventure.R;
import com.example.ptitadventure.model.Subtask;
import java.util.List;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private List<Subtask> subtasks;
    private OnSubtaskClickListener listener;

    public SubtaskAdapter(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public interface OnSubtaskClickListener {
        void onSubtaskClick(Subtask subtask);
    }

    public void setOnSubtaskClickListener(OnSubtaskClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubtaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subtask, parent, false);
        return new SubtaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtaskViewHolder holder, int position) {
        Subtask subtask = subtasks.get(position);
        holder.bind(subtask);
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    public class SubtaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textSubtaskName;
        private TextView textSubtaskStatus;

        public SubtaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubtaskName = itemView.findViewById(R.id.text_subtask_name);
            textSubtaskStatus = itemView.findViewById(R.id.text_subtask_status);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onSubtaskClick(subtasks.get(position));
                }
            });
        }

        public void bind(Subtask subtask) {
            textSubtaskName.setText(subtask.getDescription());
            textSubtaskStatus.setText(subtask.isCompleted() ? "Hoàn thành" : "Chưa hoàn thành");
        }
    }
}