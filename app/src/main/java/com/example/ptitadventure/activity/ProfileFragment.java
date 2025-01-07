package com.example.ptitadventure.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.QuestAdapter;
import com.example.ptitadventure.api.ApiProfileService;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.dao.UserRepo;
import com.example.ptitadventure.model.Quest;
import com.example.ptitadventure.model.StudentQuest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private CircleImageView imageProfile;
    private TextView textProfileName, textProfileScore, textProfileRank, textTaskCompleted;
    private RecyclerView recyclerViewProfile;
    private QuestAdapter profileAdapter;
    private List<Quest> profileList;
    private QuestDAO questDAO;
    private int studentID;
    private UserRepo userRepo;
    private MaterialButton buttonChangePassword, buttonLogout, buttonResetProgress, buttonDeleteStudent;
    private CoordinatorLayout coordinatorLayout;
    private String role;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        questDAO = new QuestDAO(Client.getClient().create(ApiQuestService.class));
        userRepo = new UserRepo(Client.getClient().create(ApiProfileService.class));

        // 1) Read the basic info from the Intent
        Intent intent = requireActivity().getIntent();
        role = intent.getStringExtra("role");
        email = intent.getStringExtra("email");

        // 2) Check if "staff"
        if ("staff".equals(role) && getArguments() != null) {
            // Staff clicked on a student's profile from the list
            // Read from the Bundle arguments
            studentID = getArguments().getInt("studentId", -1);
            // If needed, you can also read "role" again from arguments
            // but typically you'd keep it as "staff"
        } else {
            // Not staff, or no arguments passed
            // => The user must be the student who logged in
            studentID = Integer.parseInt(intent.getStringExtra("studentID"));
        }

        initViews(view);
        setupButtonListeners();

        // 3) Adjust the UI based on the role
        if ("staff".equals(role)) {
            // Staff is viewing a student's profile
            setupStaffView();
            profileDataUpdateXX(studentID); // or however you load the data
            getCheckListXX("completed", studentID);
        } else {
            // Logged in as a normal student
            profileDataUpdate(); // loads the logged-in student's info
            getCheckList("completed");
        }

        return view;
    }

    private void initViews(View view) {
        imageProfile = view.findViewById(R.id.image_profile);
        textProfileName = view.findViewById(R.id.text_username);
        textProfileScore = view.findViewById(R.id.text_exp);
        textProfileRank = view.findViewById(R.id.text_level);
        textTaskCompleted = view.findViewById(R.id.text_completed_tasks);
        recyclerViewProfile = view.findViewById(R.id.recycler_view_achievements);
        buttonChangePassword = view.findViewById(R.id.button_change_password);
        buttonLogout = view.findViewById(R.id.button_logout);
        buttonResetProgress = view.findViewById(R.id.button_reset_progress);
        buttonDeleteStudent = view.findViewById(R.id.button_delete_student);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);

        // Ẩn các nút dành riêng cho staff
        buttonResetProgress.setVisibility(View.GONE);
        buttonDeleteStudent.setVisibility(View.GONE);
    }

    private void setupStaffView() {
        // Hiển thị các nút dành cho staff
        buttonResetProgress.setVisibility(View.VISIBLE);
        buttonDeleteStudent.setVisibility(View.VISIBLE);
        buttonChangePassword.setVisibility(View.GONE);
        buttonLogout.setVisibility(View.GONE);

        // Gắn sự kiện cho nút "Reset tiến trình"
        buttonResetProgress.setOnClickListener(v -> showResetConfirmation());
        buttonDeleteStudent.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void showResetConfirmation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Reset Tiến Trình")
                .setMessage("Bạn có chắc chắn muốn reset tiến trình của sinh viên này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> resetStudentProgress())
                .setNegativeButton("Hủy", null)
                .show();
    }
    private void showDeleteConfirmation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa Sinh Viên")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên này? Hành động này không thể hoàn tác.")
                .setPositiveButton("Đồng ý", (dialog, which) -> deleteStudent())
                .setNegativeButton("Hủy", null)
                .show();
    }


    private void profileDataUpdateXX(int stuID) {
        userRepo.getProfileDataByID(String.valueOf(stuID), new Callback<StudentQuest>() {
            @Override
            public void onResponse(Call<StudentQuest> call, Response<StudentQuest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StudentQuest studentQuest = response.body();
                    textProfileName.setText(studentQuest.getStudents().getFullName());
                    textProfileScore.setText(String.valueOf(studentQuest.getScore()));
                    textProfileRank.setText(String.valueOf(calculateLevel(studentQuest.getScore())));
                } else {
                    showSnackbar("Không thể tải thông tin sinh viên.");
                }
            }

            @Override
            public void onFailure(Call<StudentQuest> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void profileDataUpdate() {
        userRepo.getProfileDataByID(String.valueOf(studentID), new Callback<StudentQuest>() {
            @Override
            public void onResponse(Call<StudentQuest> call, Response<StudentQuest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StudentQuest studentQuest = response.body();
                    textProfileName.setText(studentQuest.getStudents().getFullName());
                    textProfileScore.setText(String.valueOf(studentQuest.getScore()));
                    textProfileRank.setText(String.valueOf(calculateLevel(studentQuest.getScore())));
                } else {
                    showSnackbar("Không thể tải thông tin sinh viên.");
                }
            }

            @Override
            public void onFailure(Call<StudentQuest> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void resetStudentProgress() {
        ApiProfileService apiService = Client.getClient().create(ApiProfileService.class);
        apiService.resetProgress(String.valueOf(studentID)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSnackbar("Tiến trình đã được reset.");
                } else {
                    showSnackbar("Không thể reset tiến trình.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackbar("Lỗi: " + t.getMessage());
            }
        });
    }

    private void deleteStudent() {
        ApiProfileService apiService = Client.getClient().create(ApiProfileService.class);
        apiService.deleteStudent(String.valueOf(studentID)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSnackbar("Sinh viên đã bị xóa.");
                    requireActivity().onBackPressed();
                } else {
                    showSnackbar("Không thể xóa sinh viên.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackbar("Lỗi: " + t.getMessage());
            }
        });
    }

    private void getCheckListXX(String status, int stuID) {
        questDAO.getQuestsByStatus(stuID, status, new Callback<List<Quest>>() {
            @Override
            public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    profileList = response.body();
                    textTaskCompleted.setText(String.valueOf(profileList.size()));
                    profileAdapter = new QuestAdapter(profileList, stuID, questDAO);
                    recyclerViewProfile.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewProfile.setAdapter(profileAdapter);
                } else {
                    showSnackbar("Không thể tải danh sách nhiệm vụ.");
                }
            }

            @Override
            public void onFailure(Call<List<Quest>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void getCheckList(String status) {
        questDAO.getQuestsByStatus(studentID, status, new Callback<List<Quest>>() {
            @Override
            public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    profileList = response.body();
                    textTaskCompleted.setText(String.valueOf(profileList.size()));
                    profileAdapter = new QuestAdapter(profileList, studentID, questDAO);
                    recyclerViewProfile.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewProfile.setAdapter(profileAdapter);
                } else {
                    showSnackbar("Không thể tải danh sách nhiệm vụ.");
                }
            }

            @Override
            public void onFailure(Call<List<Quest>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void setupButtonListeners() {
        buttonChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("role", role);
            intent.putExtra("student_id", studentID);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            showSnackbar("Đăng xuất thành công.");
            coordinatorLayout.postDelayed(() -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }, 1000);
        });
    }

    private int calculateLevel(int totalScore) {
        return (totalScore / 100) + 1;
    }

    private void showSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
