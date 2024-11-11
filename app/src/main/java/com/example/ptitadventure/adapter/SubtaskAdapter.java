package com.example.ptitadventure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ptitadventure.R;
import com.example.ptitadventure.activity.CustomSnackbar;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.model.Quest;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubtaskAdapter extends RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder> {

    private List<Quest> subtasks;
    private OnSubtaskClickListener listener;
    private QuestDAO questDAO;
    private int studentID;

    public SubtaskAdapter(List<Quest> subtasks, QuestDAO apiService, int studentID) {
        this.subtasks = subtasks;
        this.studentID = studentID;
        this.questDAO = apiService;
    }

    public interface OnSubtaskClickListener {
        void onSubtaskClick(Quest subtask);
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
        Quest subtask = subtasks.get(position);
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

        public void bind(Quest subtask) {
            textSubtaskName.setText(subtask.getName());
            questDAO.getQuestCompleted(studentID, subtask.getId(), new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        updateUI(response.body());
                    } else {
                        CustomSnackbar.make(itemView,
                                "Failed to get subtask completion status",
                                Snackbar.LENGTH_SHORT,
                                R.drawable.ic_error);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    CustomSnackbar.make(itemView,
                            "Failed to get subtask completion status",
                            Snackbar.LENGTH_SHORT,
                            R.drawable.ic_error);
                }
            });
        }

        void updateUI(int questCompleted) {
            if (questCompleted != 0) {
                textSubtaskStatus.setText("Hoàn thành");
            } else {
                textSubtaskStatus.setText("Chưa hoàn thành");
            }
        }
    }
}
