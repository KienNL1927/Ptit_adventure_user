package com.example.ptitadventure.dao;

import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.ApiQuestionService;
import com.example.ptitadventure.model.Quiz;

import retrofit2.Callback;

public class QuestRepo {
    private ApiQuestionService apiQuestionService;

    public QuestRepo(ApiQuestionService apiQuestionService) {
        this.apiQuestionService = apiQuestionService;
    }

    public void getSubQuest(int mainQuest, Callback<Quiz> callback) {
        apiQuestionService.getQuiz(mainQuest).enqueue(callback);
    }


}
