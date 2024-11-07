package com.example.ptitadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.ptitadventure.R;
import com.example.ptitadventure.activity.CustomSnackbar;
import com.example.ptitadventure.dao.UserDAO;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;
    private CoordinatorLayout coordinatorLayout;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userDAO = new UserDAO(this);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        usernameEditText = findViewById(R.id.edit_text_username);
        passwordEditText = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_login);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidCredentials(username, password)) {
                    showSuccessSnackbar();
                } else {
                    showErrorSnackbar();
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        return userDAO.checkLogin(username, password);
    }

    private void showSuccessSnackbar() {
        CustomSnackbar.make(coordinatorLayout,
                "Đăng nhập thành công! Chào mừng bạn đến với PTIT Adventure!",
                Snackbar.LENGTH_LONG,
                R.drawable.ic_success);
        // Start MainActivity after a short delay
        coordinatorLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                int id = userDAO.getIDStudent(usernameEditText.getText().toString());
                intent.putExtra("studentID", id);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void showErrorSnackbar() {
        CustomSnackbar.make(coordinatorLayout,
                "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.",
                Snackbar.LENGTH_LONG,
                R.drawable.ic_error);
    }
}