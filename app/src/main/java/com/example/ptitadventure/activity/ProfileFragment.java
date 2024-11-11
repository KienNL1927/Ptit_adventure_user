package com.example.ptitadventure.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;

import com.example.ptitadventure.adapter.QuestAdapter;
import com.example.ptitadventure.api.ApiProfileService;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.dao.StudentQuestRepo;
import com.example.ptitadventure.dao.UserRepo;
import com.example.ptitadventure.model.Quest;
import com.example.ptitadventure.model.Student;
import com.example.ptitadventure.model.StudentQuest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        questDAO = new QuestDAO(Client.getClient().create(ApiQuestService.class));
        studentID = getActivity().getIntent().getIntExtra("studentID", 1);
        userRepo = new UserRepo(Client.getClient().create(ApiProfileService.class));

        initViews(view);
        profileDataUpdate();



        getCheckList("completed");

        return view;
    }

    private int calculateLevel(int totalScore) {
        return (totalScore / 100) + 1;
    }

    private void initViews(View view) {
        imageProfile = view.findViewById(R.id.image_profile);
        textProfileName = view.findViewById(R.id.text_username);
        textProfileScore = view.findViewById(R.id.text_exp);
        textProfileRank = view.findViewById(R.id.text_level);
        textTaskCompleted = view.findViewById(R.id.text_completed_tasks);
        recyclerViewProfile = view.findViewById(R.id.recycler_view_achievements);
    }

    private void profileDataUpdate() {
       userRepo.getProfileDataByID(String.valueOf(studentID), new Callback<StudentQuest>() {
           @Override
           public void onResponse(Call<StudentQuest> call, Response<StudentQuest> response) {
                if (response.isSuccessful()) {
                     StudentQuest studentQuest = response.body();
                     textProfileName.setText(studentQuest.getStudents().getFullName());
                     textProfileScore.setText(String.valueOf(studentQuest.getScore()));
                     textProfileRank.setText(String.valueOf(calculateLevel(studentQuest.getScore())));
                } else {
                     CustomSnackbar.make(getView(),
                            "Có lỗi với server",
                            Snackbar.LENGTH_SHORT,
                            R.drawable.ic_error);
                }
           }

           @Override
           public void onFailure(Call<StudentQuest> call, Throwable t) {
               CustomSnackbar.make(getView(),
                       "Có lỗi với server",
                       Snackbar.LENGTH_SHORT,
                       R.drawable.ic_error);
           }
       });
    }

    void getCheckList(String status) {
        questDAO.getQuestsByStatus(studentID, status, new Callback<List<Quest>>() {
            @Override
            public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
                if (response.isSuccessful()) {
                    if (status.equals("completed")) {
                        if (response.body() == null) {
                            textTaskCompleted.setText(String.valueOf(0));
                            return;
                        }
                        profileList = response.body();
                        textTaskCompleted.setText(String.valueOf(profileList.size()));
                        profileAdapter = new QuestAdapter(profileList, studentID, questDAO);
                        recyclerViewProfile.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewProfile.setAdapter(profileAdapter);
                    }

                } else {
                    CustomSnackbar.make(getView(),
                            "Có lỗi với server",
                            Snackbar.LENGTH_SHORT,
                            R.drawable.ic_error);
                }
            }

            @Override
            public void onFailure(Call<List<Quest>> call, Throwable t) {
                CustomSnackbar.make(getView(),
                        "Có lỗi với server",
                        Snackbar.LENGTH_SHORT,
                        R.drawable.ic_error);
            }
        });
    }
}