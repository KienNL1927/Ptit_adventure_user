package com.example.ptitadventure.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ptitadventure.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class QuestCompletionDialog extends DialogFragment {

    private static final String ARG_QUEST_NAME = "quest_name";
    private static final String ARG_POINTS = "points";

    private String questName;
    private int points;
    private OnQuestCompletionListener listener;

    public interface OnQuestCompletionListener {
        void onQuestCompleted(int points);
    }

    public static QuestCompletionDialog newInstance(String questName, int points) {
        QuestCompletionDialog fragment = new QuestCompletionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_QUEST_NAME, questName);
        args.putInt(ARG_POINTS, points);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questName = getArguments().getString(ARG_QUEST_NAME);
            points = getArguments().getInt(ARG_POINTS);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_quest_completion, null);

        TextView textQuestName = view.findViewById(R.id.text_quest_name);
        TextView textPoints = view.findViewById(R.id.text_points);
        Button buttonOk = view.findViewById(R.id.button_ok);

        textQuestName.setText(questName);
        textPoints.setText(getString(R.string.points_earned, points));

        buttonOk.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuestCompleted(points);
            }
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }

    public void setOnQuestCompletionListener(OnQuestCompletionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (listener != null) {
            listener.onQuestCompleted(points);
        }
    }
}