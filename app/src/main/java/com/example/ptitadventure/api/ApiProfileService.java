package com.example.ptitadventure.api;

import com.example.ptitadventure.model.StudentQuest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiProfileService {

    @GET("user/student/{studentID}")
    public Call<StudentQuest> getProfileDataByID(@Path("studentID") String studentID);
}
