package com.example.ptitadventure.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.QuestionAdapter;
import com.example.ptitadventure.api.ApiQuestionService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestRepo;
import com.example.ptitadventure.model.Question;
import com.example.ptitadventure.model.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView recyclerViewQuestions;
    private Button buttonSubmit;
    private QuestionAdapter adapter;
    private List<Question> Questions;
    private QuestRepo questRepo;
    private int questID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questRepo = new QuestRepo(Client.getClient().create(ApiQuestionService.class));
        questID = getIntent().getIntExtra("quest_id", 1);

        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        getQuestions(questID, this);

        buttonSubmit.setOnClickListener(v -> submitQuiz());
    }

    private void submitQuiz() {
        int score = 0;
        for (int i = 0; i < Questions.size(); i++) {
            Question question = Questions.get(i);
            int selectedAnswer = adapter.getSelectedAnswer(i);
            for (int j = 0; j < question.getChoices().size(); j++) {
                if (selectedAnswer == j && question.getChoices().get(i).isCorrect()) {
                    score += 100;
                    break;
                }
            }
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("quiz_score", score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void getQuestions(int questID, Context context) {
       questRepo.getSubQuest(questID, new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {
                    Quiz quiz = response.body();
                    Questions = quiz.getQuestions();
                    adapter = new QuestionAdapter(Questions);
                    recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(context));
                    recyclerViewQuestions.setAdapter(adapter);
                } else {
                    Toast.makeText(QuizActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}