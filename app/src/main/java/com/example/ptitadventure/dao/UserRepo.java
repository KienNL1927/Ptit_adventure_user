package com.example.ptitadventure.dao;

import com.example.ptitadventure.api.ApiProfileService;
import com.example.ptitadventure.model.StudentQuest;

import retrofit2.Call;
import retrofit2.Callback;

public class UserRepo {
    private ApiProfileService apiProfileService;

    public UserRepo(ApiProfileService apiProfileService) {
        this.apiProfileService = apiProfileService;
    }

    public void getProfileDataByID(String studentID, Callback<StudentQuest> callback) {
        Call<StudentQuest> profile = apiProfileService.getProfileDataByID(studentID);
        profile.enqueue(callback);
    }
}
