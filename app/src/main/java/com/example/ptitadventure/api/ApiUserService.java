package com.example.ptitadventure.api;


import com.example.ptitadventure.DTO.AllStudentDTO;
import com.example.ptitadventure.DTO.ForgotPasswordRequest;
import com.example.ptitadventure.DTO.StaffResponse;
import com.example.ptitadventure.DTO.UserResponse;
import com.example.ptitadventure.model.Staff;
import com.example.ptitadventure.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiUserService {

    @GET("user")
    Call<UserResponse> checkLogin(@Query("email") String email, @Query("password") String password);

    // Thay đổi mật khẩu
    @GET("user/change_password")
    Call<UserResponse> changePassword(@Query("role") String role, @Query("id") int StudentID,@Query("password") String newPassword);

    @GET("user/all_student")
    Call<List<AllStudentDTO>> getStudents();

    @GET("user/staff/{staff_id}")
    Call<StaffResponse> getStaffByID(@Path("staff_id") String staffID);

    @POST("api/forgot-password")
    Call<Void> forgotPassword(@Body ForgotPasswordRequest request);

}
