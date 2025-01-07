package com.example.ptitadventure.activity;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.ptitadventure.DTO.StaffResponse;
import com.example.ptitadventure.R;

import com.example.ptitadventure.adapter.QuestAdapter;
import com.example.ptitadventure.api.ApiProfileService;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.api.ApiUserService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.dao.StudentQuestRepo;
import com.example.ptitadventure.dao.UserRepo;
import com.example.ptitadventure.model.Quest;
import com.example.ptitadventure.model.Staff;
import com.example.ptitadventure.model.Student;
import com.example.ptitadventure.model.StudentQuest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffProfileFragment extends Fragment {

    private CircleImageView imageProfile;
    private TextView textProfileName, textStaffPosition;
    private RecyclerView recyclerViewProfile;
    private int staffID;
    private UserRepo userRepo;
    private MaterialButton buttonChangePassword, buttonLogout;
    private CoordinatorLayout coordinatorLayout;
    private String role;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_profile, container, false);
        userRepo = new UserRepo(Client.getClient().create(ApiUserService.class));
        email =  getActivity().getIntent().getStringExtra("email");
        staffID = getActivity().getIntent().getIntExtra("staffID", 1);
        role = getActivity().getIntent().getStringExtra("role");

        initViews(view);
        profileDataUpdate();

        setupButtonListeners();

        return view;
    }


    private void initViews(View view) {
        imageProfile = view.findViewById(R.id.image_profile);
        textProfileName = view.findViewById(R.id.text_username);
        textStaffPosition = view.findViewById(R.id.text_staff_position);
        buttonChangePassword = view.findViewById(R.id.button_change_password);
        buttonLogout = view.findViewById(R.id.button_logout);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);

    }

    private void profileDataUpdate() {
        userRepo.getStaffByID(String.valueOf(staffID), new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                if (response.isSuccessful()) {
                    StaffResponse staffInfo = response.body();
                    textProfileName.setText(staffInfo.getName());
                    textStaffPosition.setText(staffInfo.getFaculty());

                } else {
                    CustomSnackbar.make(getView(),
                            "Có lỗi với server",
                            Snackbar.LENGTH_SHORT,
                            R.drawable.ic_error);
                }
            }

            @Override
            public void onFailure(Call<StaffResponse> call, Throwable t) {
                CustomSnackbar.make(getView(),
                        "Có lỗi với server",
                        Snackbar.LENGTH_SHORT,
                        R.drawable.ic_error);
            }
        });
    }

    void setupButtonListeners() {
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("role", role);
                intent.putExtra("student_id", staffID);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getView() != null && getActivity() != null) {
                    CustomSnackbar.make(getView(),
                            "Đăng xuất thành công",
                            Snackbar.LENGTH_LONG,
                            R.drawable.ic_success);
                }
                coordinatorLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, 1000);
            }
        });
    }
}