package com.example.ptitadventure.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.QuestAdapter;
import com.example.ptitadventure.api.ApiQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.QuestDAO;
import com.example.ptitadventure.model.Quest;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistFragment extends Fragment {

    private TabLayout tabLayout;
    private RecyclerView recyclerViewCheckpoints;
    private QuestAdapter checkpointAdapter;
    private List<Quest> incompleteCheckpoints;
    private List<Quest> completedCheckpoints;
    private QuestDAO questDAO;
    private int studentID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checklist, container, false);
        questDAO = new QuestDAO(Client.getClient().create(ApiQuestService.class));
        studentID = getActivity().getIntent().getIntExtra("studentID", 1);

        tabLayout = view.findViewById(R.id.tab_layout);
        recyclerViewCheckpoints = view.findViewById(R.id.recycler_view_tasks);

        incompleteCheckpoints = new ArrayList<>();
        completedCheckpoints = new ArrayList<>();

        getCheckList("completed");
        getCheckList("incomplete");


        recyclerViewCheckpoints.setLayoutManager(new LinearLayoutManager(getContext()));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    checkpointAdapter.updateQuests(incompleteCheckpoints);
                } else {
                    checkpointAdapter.updateQuests(completedCheckpoints);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }

    void getCheckList(String status) {
        questDAO.getQuestsByStatus(studentID, status, new Callback<List<Quest>>() {
            @Override
            public void onResponse(Call<List<Quest>> call, Response<List<Quest>> response) {
                if (response.isSuccessful()) {
                    if (status.equals("completed")) {
                        if (response.body() == null) {
                            return;
                        }
                        completedCheckpoints = response.body();
                        checkpointAdapter = new QuestAdapter(completedCheckpoints, studentID, questDAO);
                    } else {
                        if (response.body() == null) {
                            return;
                        }
                        incompleteCheckpoints = response.body();
                        checkpointAdapter = new QuestAdapter(incompleteCheckpoints, studentID, questDAO);
                    }
                    recyclerViewCheckpoints.setAdapter(checkpointAdapter);
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