package com.example.ptitadventure.dao;

import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.model.Quest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class QuestDAO {

    private ApiQuestService apiService;

    public QuestDAO(ApiQuestService apiService) {
        this.apiService = apiService;

    }

    public void getMainQuest(final Callback<List<Quest>> callback) {
        Call<List<Quest>> call = apiService.getMainQuest();
        call.enqueue(callback);
    }

    public void getSubQuest(int parent_id, final Callback<List<Quest>> callback) {
       Call<List<Quest>> call = apiService.getSubQuest(parent_id);
       call.enqueue(callback);
    }

    public void getQuestCompleted(int id, int questID, final Callback<Integer> callback) {
        Call<Integer> call = apiService.getQuestCompleted(id, questID);
        call.enqueue(callback);
    }

    public void getTotalQuest(int questID, final Callback<Integer> callback) {
        Call<Integer> call = apiService.getTotalQuest(questID);
        call.enqueue(callback);
    }

    public void getQuestsByStatus(int studentID, String status, final Callback<List<Quest>> callback) {
        Call<List<Quest>> call = apiService.getQuestsByStatus(studentID, status);
        call.enqueue(callback);
    }

    public void checkIfMainQuestIfCompleted(int studentID, int questID, Callback<Boolean> callback) {
        apiService.checkIfMainQuestIfCompleted(studentID, questID).enqueue(callback);
    }
}
