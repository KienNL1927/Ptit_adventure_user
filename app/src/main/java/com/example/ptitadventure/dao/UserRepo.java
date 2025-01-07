package com.example.ptitadventure.dao;

import com.example.ptitadventure.DTO.AllStudentDTO;
import com.example.ptitadventure.DTO.ForgotPasswordRequest;
import com.example.ptitadventure.DTO.StaffResponse;
import com.example.ptitadventure.DTO.UserResponse;
import com.example.ptitadventure.api.ApiProfileService;
import com.example.ptitadventure.api.ApiUserService;
import com.example.ptitadventure.model.Staff;
import com.example.ptitadventure.model.Student;
import com.example.ptitadventure.model.StudentQuest;
import com.example.ptitadventure.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UserRepo {
    private ApiProfileService apiProfileService;
    private ApiUserService apiUserService;

    public UserRepo(ApiProfileService apiProfileService) {
        this.apiProfileService = apiProfileService;
    }

    public void getProfileDataByID(String studentID, Callback<StudentQuest> callback) {
        Call<StudentQuest> profile = apiProfileService.getProfileDataByID(studentID);
        profile.enqueue(callback);
    }


    public UserRepo(ApiUserService apiUserService) {

        this.apiUserService = apiUserService;
    }

    public void getLoginData(String email, Callback<UserResponse> callback, String password){
        Call<UserResponse> login = apiUserService.checkLogin(email, password);
        login.enqueue(callback);
    }
    public void changePassword(String role,int id, String newPassword, Callback<UserResponse> callback) {
        Call<UserResponse> call = apiUserService.changePassword(role, id, newPassword);
        call.enqueue(callback);
    }

    public void getStudentList(Callback<List<AllStudentDTO>> callback) {
        Call<List<AllStudentDTO>> call = apiUserService.getStudents();
        call.enqueue(callback);
    }

    public void getStaffByID(String id, Callback<StaffResponse> callback) {
        Call<StaffResponse> call = apiUserService.getStaffByID(id);
        call.enqueue(callback);
    }

    public void forgotPassword(String emailOrUsername, Callback<Void> callback) {
        ForgotPasswordRequest request = new ForgotPasswordRequest(emailOrUsername);
        apiUserService.forgotPassword(request).enqueue(callback);
    }

}
