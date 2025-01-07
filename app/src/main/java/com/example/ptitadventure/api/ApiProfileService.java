package com.example.ptitadventure.api;

import com.example.ptitadventure.model.Staff;
import com.example.ptitadventure.model.StudentQuest;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiProfileService {

    @GET("user/student/{studentID}")
    public Call<StudentQuest> getProfileDataByID(@Path("studentID") String studentID);

    @GET("user/staff/{staffID}")
    public Call<Staff> getStaffID(@Path("staffID") String staffID);

    // Example using Retrofit for a DELETE endpoint
    @DELETE("user/student/{studentID}")
    Call<Void> deleteStudent(@Path("studentID") String studentID);

    // Example using Retrofit for a PATCH endpoint
    @PATCH("user/student/{studentID}/reset-progress")
    Call<Void> resetProgress(@Path("studentID") String studentID);

}
