package com.example.ptitadventure.activity;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.ptitadventure.DTO.UserResponse;
import com.example.ptitadventure.R;
import com.example.ptitadventure.activity.CustomSnackbar;
import com.example.ptitadventure.api.ApiUserService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.UserDAO;
import com.example.ptitadventure.dao.UserRepo;
import com.example.ptitadventure.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;
    private CoordinatorLayout coordinatorLayout;
    private UserRepo userRepo;
    private TextView forgotPasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userRepo = new UserRepo(Client.getClient().create(ApiUserService.class));

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        usernameEditText = findViewById(R.id.edit_text_username);
        passwordEditText = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_login);
        forgotPasswordText = findViewById(R.id.forgot_password_text);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                userRepo.getLoginData(username, new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body();
                            String email = username;
                            showLoginSuccessSnackbar(userResponse, email);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        showErrorSnackbar("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.");
                    }
                }, password);
            }
        });

        forgotPasswordText.setOnClickListener(v -> {
            String emailOrUsername = usernameEditText.getText().toString().trim();

            if (emailOrUsername.isEmpty()) {
                showErrorSnackbar("Vui lòng nhập email hoặc tên đăng nhập để khôi phục mật khẩu.");
                return;
            }

            userRepo.forgotPassword(emailOrUsername, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Generic success message for reset
                        showSuccessSnackbar("Một email khôi phục mật khẩu đã được gửi.");
                    } else {
                        showErrorSnackbar("Không thể gửi email khôi phục. Vui lòng thử lại.");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    showErrorSnackbar("Đã xảy ra lỗi khi gửi yêu cầu khôi phục.");
                }
            });
        });
    }

    private void showLoginSuccessSnackbar(UserResponse userResponse, String email) {
        if (!userResponse.getRole().isEmpty()) {
            // Show a login success message
            CustomSnackbar.make(
                    coordinatorLayout,
                    "Đăng nhập thành công! Chào mừng bạn đến với PTIT Adventure!",
                    Snackbar.LENGTH_LONG,
                    R.drawable.ic_success
            );

            // Navigate to MainActivity after a short delay
            coordinatorLayout.postDelayed(() -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                if (userResponse.getRole().equals("student")) {
                    intent.putExtra("studentID", userResponse.getStudent_id());
                } else {
                    intent.putExtra("staffID", userResponse.getStaff_id());
                }
                intent.putExtra("role", userResponse.getRole());
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }, 2000);

        } else {
            // If 'role' is empty, treat it as a login failure
            showErrorSnackbar("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.");
        }
    }
    private void showErrorSnackbar(String errorMessage) {
        CustomSnackbar.make(
                coordinatorLayout,
                errorMessage,
                Snackbar.LENGTH_LONG,
                R.drawable.ic_error
        );
    }
    private void showSuccessSnackbar(String message) {
        CustomSnackbar.make(
                coordinatorLayout,
                message,
                Snackbar.LENGTH_LONG,
                R.drawable.ic_success
        );
    }

}