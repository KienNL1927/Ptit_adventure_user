package com.example.ptitadventure.adapter;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questions;
    private SparseIntArray selectedAnswers;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
        this.selectedAnswers = new SparseIntArray();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question, position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public int getSelectedAnswer(int position) {
        return selectedAnswers.get(position, -1);
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewQuestion;
        private RadioGroup radioGroupAnswers;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
            radioGroupAnswers = itemView.findViewById(R.id.radioGroupAnswers);
        }

        void bind(Question question, int position) {
            textViewQuestion.setText(String.format("Câu hỏi %d: %s", position + 1, question.getText()));
            radioGroupAnswers.removeAllViews();
            for (int i = 0; i < question.getChoices().size(); i++) {
                RadioButton rb = new RadioButton(itemView.getContext());
                rb.setText(question.getChoices().get(i).getText());
                rb.setId(i);
                radioGroupAnswers.addView(rb);
            }
            radioGroupAnswers.setOnCheckedChangeListener((group, checkedId) ->
                    selectedAnswers.put(position, checkedId));

            int selectedAnswer = selectedAnswers.get(position, -1);
            if (selectedAnswer != -1) {
                radioGroupAnswers.check(selectedAnswer);
            }
        }
    }
}