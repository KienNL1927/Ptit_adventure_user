/*
package com.example.ptitadventure.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.SubtaskAdapter;
import com.example.ptitadventure.util.SubtaskInstructionsDialog;

import java.util.ArrayList;
import java.util.List;

public class SubtaskActivity extends AppCompatActivity implements SubtaskAdapter.OnSubtaskClickListener, QuestCompletionDialog.OnQuestCompletionListener {

    private RecyclerView recyclerViewSubtasks;
    private SubtaskAdapter subtaskAdapter;
    private List<Subtask> subtasks;
    private CheckPoint checkpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);

        recyclerViewSubtasks = findViewById(R.id.recycler_view_subtasks);
        recyclerViewSubtasks.setLayoutManager(new LinearLayoutManager(this));

        String checkpointId = getIntent().getStringExtra("checkpoint_id");
        checkpoint = getCheckpointById(checkpointId);

        subtasks = checkpoint.getSubtasks();
        subtaskAdapter = new SubtaskAdapter(subtasks);
        subtaskAdapter.setOnSubtaskClickListener(this);
        recyclerViewSubtasks.setAdapter(subtaskAdapter);
    }

    @Override
    public void onSubtaskClick(Subtask subtask) {
        showSubtaskInstructionsDialog(subtask);
    }

    private void showSubtaskInstructionsDialog(Subtask subtask) {
        SubtaskInstructionsDialog dialog = new SubtaskInstructionsDialog(this, subtask);
        dialog.setOnNfcTagScannedListener(() -> {
            subtask.setCompleted(true);
            subtaskAdapter.notifyDataSetChanged();
            checkAllSubtasksCompleted();
        });
        dialog.show();
    }

    private void checkAllSubtasksCompleted() {
        boolean allCompleted = true;
        for (Subtask subtask : subtasks) {
            if (!subtask.isCompleted()) {
                allCompleted = false;
                break;
            }
        }
        if (allCompleted) {
            int points = calculatePoints();
            showCompletionDialog(points);
        }
    }

    private int calculatePoints() {
        return subtasks.size() * 10;
    }

    private void showCompletionDialog(int points) {
        QuestCompletionDialog dialog = QuestCompletionDialog.newInstance(checkpoint.getName(), points);
        dialog.setOnQuestCompletionListener(this);
        dialog.show(getSupportFragmentManager(), "quest_completion_dialog");
    }

    @Override
    public void onQuestCompleted(int points) {
        updateUserScore(points);
        finish();
    }

    private void updateUserScore(int points) {
        // TODO: Implement this method to update the user's score in the database
        Toast.makeText(this, "Score updated: +" + points + " points", Toast.LENGTH_SHORT).show();
    }

    private CheckPoint getCheckpointById(String checkpointId) {
        // Dummy implementation
        CheckPoint checkpoint = new CheckPoint(checkpointId, "Dummy Checkpoint", "Description", 5, null, null, 0, false);
        List<Subtask> dummySubtasks = new ArrayList<>();
        dummySubtasks.add(new Subtask("1", "Subtask 1", false, 2));
        dummySubtasks.add(new Subtask("2", "Subtask 2", false, 3));
        dummySubtasks.add(new Subtask("3", "Subtask 3", false, 4));
        checkpoint.setSubtasks(dummySubtasks);
        return checkpoint;
    }
}*/
