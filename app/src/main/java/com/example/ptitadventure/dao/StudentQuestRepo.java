package com.example.ptitadventure.dao;

import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.model.StudentQuest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class StudentQuestRepo {
    private ApiStudentQuestService apiService;

    public StudentQuestRepo(ApiStudentQuestService apiService) {
        this.apiService = apiService;
    }

    public void getDataLeaderboard(final Callback<List<StudentQuest>> callback) {
        Call<List<StudentQuest>> call = apiService.getDataLeaderboard();
        call.enqueue(callback);
    }

    public void saveProgress(int studentID, int questID, int point) {
        apiService.saveProgress(studentID, questID, point);
    }
}
