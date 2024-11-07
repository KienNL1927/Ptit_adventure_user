package com.example.ptitadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptitadventure.R;
import com.example.ptitadventure.adapter.QuestAdapter;
import com.example.ptitadventure.model.Location;
import com.example.ptitadventure.model.Quest;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private MaterialCardView checkpointA1, checkpointA2, checkpointA3;
    private Button buttonScanNfc;
    private RecyclerView recyclerViewLocations;

    private com.example.ptitadventure.dao.QuestDAO questDAO;
    private int studentID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        questDAO = new com.example.ptitadventure.dao.QuestDAO(getContext());
        studentID = getActivity().getIntent().getIntExtra("studentID", 1);

        checkpointA1 = view.findViewById(R.id.checkpoint_a1);
        checkpointA2 = view.findViewById(R.id.checkpoint_a2);
        checkpointA3 = view.findViewById(R.id.checkpoint_a3);
        buttonScanNfc = view.findViewById(R.id.button_scan_nfc);
        recyclerViewLocations = view.findViewById(R.id.recycler_view_locations);

        setupCheckpoints();
        setupNfcButton();
        setupRecyclerView();

        return view;
    }

    private void setupCheckpoints() {
        checkpointA1.setOnClickListener(v -> openLocationQuests("A1", 5));
        checkpointA2.setOnClickListener(v -> openLocationQuests("A2", 4));
        checkpointA3.setOnClickListener(v -> openLocationQuests("A3", 3));
    }

    private void setupNfcButton() {
        buttonScanNfc.setOnClickListener(v -> {
            // Implement NFC scanning logic here
            CustomSnackbar.make(getView(),
                    "NFC scanning not implemented yet",
                    Snackbar.LENGTH_SHORT,
                    R.drawable.ic_info);
        });
    }

    private void setupRecyclerView() {
        List<Quest> list;
        list = questDAO.getMainQuest();
        QuestAdapter adapter = new QuestAdapter(list, studentID, questDAO);
        adapter.setOnLocationClickListener(location -> {
           /* Intent intent = new Intent(getActivity(), SubtaskActivity.class);
            startActivity(intent);*/
        });
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLocations.setAdapter(adapter);
    }

    private void openLocationQuests(String location, int floors) {
        if (location.equals("A2") || location.equals("A3")) {
            CustomSnackbar.make(getView(),
                    "This location is further for development",
                    Snackbar.LENGTH_SHORT,
                    R.drawable.ic_info);
            return;
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("location", location);
        intent.putExtra("floors", floors);
        startActivity(intent);
    }

}