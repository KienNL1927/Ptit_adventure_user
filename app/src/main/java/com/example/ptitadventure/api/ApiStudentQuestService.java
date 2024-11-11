package com.example.ptitadventure.api;

import com.example.ptitadventure.model.StudentQuest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiStudentQuestService {

    @GET("student-quest/leaderboard")
    public Call<List<StudentQuest>> getDataLeaderboard();

    @POST("student-quest/save-progress")
    public void saveProgress(@Field("student_id") int studentID, @Field("quest_id") int questID, @Field("score") int point);
}
