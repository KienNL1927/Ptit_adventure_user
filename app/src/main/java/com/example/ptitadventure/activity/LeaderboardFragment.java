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
import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.StudentQuestRepo;
import com.example.ptitadventure.model.Student;
import com.example.ptitadventure.model.StudentQuest;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends Fragment {

    private CircleImageView imageFirstPlace, imageSecondPlace, imageThirdPlace;
    private TextView textFirstPlaceName, textSecondPlaceName, textThirdPlaceName;
    private TextView textFirstPlaceScore, textSecondPlaceScore, textThirdPlaceScore;
    private RecyclerView recyclerViewLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;
    private List<StudentQuest> leaderboardList;

    private StudentQuestRepo studentQuestRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        studentQuestRepo = new StudentQuestRepo(Client.getClient().create(ApiStudentQuestService.class));

        initViews(view);
        setupRecyclerView();
        leaderBoardDataUpdate();

        return view;
    }

    private void initViews(View view) {
        imageFirstPlace = view.findViewById(R.id.image_first_place);
        imageSecondPlace = view.findViewById(R.id.image_second_place);
        imageThirdPlace = view.findViewById(R.id.image_third_place);

        textFirstPlaceName = view.findViewById(R.id.text_first_place_name);
        textSecondPlaceName = view.findViewById(R.id.text_second_place_name);
        textThirdPlaceName = view.findViewById(R.id.text_third_place_name);

        textFirstPlaceScore = view.findViewById(R.id.text_first_place_score);
        textSecondPlaceScore = view.findViewById(R.id.text_second_place_score);
        textThirdPlaceScore = view.findViewById(R.id.text_third_place_score);

        recyclerViewLeaderboard = view.findViewById(R.id.recycler_view_leaderboard);
    }

    private void leaderBoardDataUpdate() {
        studentQuestRepo.getDataLeaderboard(new Callback<List<StudentQuest>>() {
            @Override
            public void onResponse(Call<List<StudentQuest>> call, Response<List<StudentQuest>> response) {
                if (response.isSuccessful()) {
                    List<StudentQuest> studentQuests = response.body();
                    loadLeaderboardData(studentQuests);
                } else {
                    CustomSnackbar.make(getView(),
                            "Không thể tải dữ liệu bảng xếp hạng",
                            Snackbar.LENGTH_SHORT,
                            R.drawable.ic_error);
                }
            }

            @Override
            public void onFailure(Call<List<StudentQuest>> call, Throwable t) {
                CustomSnackbar.make(getView(),
                        "Không thể tải dữ liệu bảng xếp hạng",
                        Snackbar.LENGTH_SHORT,
                        R.drawable.ic_error);
            }
        });
    }

    private void setupRecyclerView() {
        leaderboardList = new ArrayList<>();
        leaderboardAdapter = new LeaderboardAdapter(leaderboardList);
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLeaderboard.setAdapter(leaderboardAdapter);
    }

    private void loadLeaderboardData(List<StudentQuest> studentQuests) {
        // In a real app, you would fetch this data from a database or API
        List<StudentQuest> allProgress = studentQuests;

        // Sort the list by total score in descending order
        Collections.sort(allProgress, (gp1, gp2) -> Integer.compare((int) gp2.getScore(), (int) gp1.getScore()));

        // Update top 3 players
        if (allProgress.size() > 0) {
            updateTopPlayer(textFirstPlaceName, textFirstPlaceScore, imageFirstPlace, allProgress.get(0));
        }
        if (allProgress.size() > 1) {
            updateTopPlayer(textSecondPlaceName, textSecondPlaceScore, imageSecondPlace, allProgress.get(1));
        }
        if (allProgress.size() > 2) {
            updateTopPlayer(textThirdPlaceName, textThirdPlaceScore, imageThirdPlace, allProgress.get(2));
        }

        // Update the RecyclerView with the remaining players
        if (allProgress.size() > 3) {
            leaderboardList.addAll(allProgress.subList(3, allProgress.size()));
            leaderboardAdapter.notifyDataSetChanged();
        }
    }

    private void updateTopPlayer(TextView nameView, TextView scoreView, CircleImageView imageView, StudentQuest progress) {
        nameView.setText(progress.getStudents().getFullName());
        scoreView.setText(progress.getScore() + " pts");
        // In a real app, you would load the student's avatar here
        // imageView.setImageResource(R.drawable.default_avatar);
    }

    private static class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

        private List<StudentQuest> leaderboardList;

        public LeaderboardAdapter(List<StudentQuest> leaderboardList) {
            this.leaderboardList = leaderboardList;
        }

        @NonNull
        @Override
        public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new LeaderboardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
            StudentQuest progress = leaderboardList.get(position);
            holder.bind(progress, position + 4);
        }

        @Override
        public int getItemCount() {
            return leaderboardList.size();
        }

        static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
            private CircleImageView imageUserAvatar;
            private TextView textUserName;
            private TextView textUserLevel;
            private TextView textUserPoints;
            private TextView textUserRank;

            public LeaderboardViewHolder(@NonNull View itemView) {
                super(itemView);
                imageUserAvatar = itemView.findViewById(R.id.image_user_avatar);
                textUserName = itemView.findViewById(R.id.text_user_name);
                textUserLevel = itemView.findViewById(R.id.text_user_level);
                textUserPoints = itemView.findViewById(R.id.text_user_points);
                textUserRank = itemView.findViewById(R.id.text_user_rank);
            }

            public void bind(StudentQuest progress, int rank) {
                textUserName.setText(progress.getStudents().getFullName());
                textUserLevel.setText("Cấp độ: " + calculateLevel(progress.getScore()));
                textUserPoints.setText(progress.getScore() + " điểm");
                textUserRank.setText("#" + rank);
                // In a real app, you would load the student's avatar here
                // imageUserAvatar.setImageResource(R.drawable.default_avatar);
            }

            private int calculateLevel(int totalScore) {
                return (totalScore / 100) + 1;
            }
        }
    }
}