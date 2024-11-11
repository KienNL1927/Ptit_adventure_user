package com.example.ptitadventure.api;

import com.example.ptitadventure.model.Quiz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiQuestionService {
    @GET("questions")
    public Call<Quiz> getQuiz(@Path("questID") int questID);
}
