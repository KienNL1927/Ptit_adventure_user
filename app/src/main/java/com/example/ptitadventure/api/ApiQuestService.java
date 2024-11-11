package com.example.ptitadventure.api;

import com.example.ptitadventure.model.Quest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiQuestService {
    @GET("quest/main")
    public Call<List<Quest>> getMainQuest();

    @GET("quest/sub/{parent_id}")
    public Call<List<Quest>> getSubQuest(@Path("parent_id") int parent_id);

    @GET("quest")
    public Call<Integer> getQuestCompleted(@Query("studentID") int studentID, @Query("questID") int questID);

    @GET("quest/{questID}")
    public Call<Integer> getTotalQuest(@Path("questID") int questID);

    @GET("quest/checklist")
    public Call<List<Quest>> getQuestsByStatus(@Query("studentID") int studentID, @Query("status") String status);

    public Call<Boolean> checkIfMainQuestIfCompleted(@Query("studentID") int studentID, @Query("questID") int questID);


}
