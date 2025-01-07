package com.example.ptitadventure.activity;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ptitadventure.DTO.UserResponse;
import com.example.ptitadventure.R;
import com.example.ptitadventure.api.ApiUserService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.UserRepo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText editTextCurrentPassword;
    private TextInputEditText editTextNewPassword;
    private TextInputEditText editTextConfirmNewPassword;
    private MaterialButton buttonChangePassword;
    private UserRepo userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        editTextCurrentPassword = findViewById(R.id.edit_text_current_password);
        editTextNewPassword = findViewById(R.id.edit_text_new_password);
        editTextConfirmNewPassword = findViewById(R.id.edit_text_confirm_new_password);
        buttonChangePassword = findViewById(R.id.button_change_password);

        userRepo = new UserRepo(Client.getClient().create(ApiUserService.class));

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmNewPassword = editTextConfirmNewPassword.getText().toString();

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu trùng khớp
        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy username từ Intent
        String username = getIntent().getStringExtra("email");
        String studentRole = getIntent().getStringExtra("role");
        int studentID = getIntent().getIntExtra("student_id", 1);
        Log.d("ChangePasswordActivity", "Username: " + username);
        Log.d("ChangePasswordActivity", "role: " + studentRole);
        Log.d("ChangePasswordActivity", "id: " + studentID);
        Log.d("ChangePasswordActivity", "Current Password: " + currentPassword);
        userRepo.getLoginData(username, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mật khẩu hiện tại đúng, tiến hành thay đổi mật khẩu
                    userRepo.changePassword(studentRole, studentID, newPassword, new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                finish(); // Đóng ChangePasswordActivity
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(ChangePasswordActivity.this, "Có lỗi xảy ra khi thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Có lỗi xảy ra khi xác minh mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
            }
        }, currentPassword);
    }
}
