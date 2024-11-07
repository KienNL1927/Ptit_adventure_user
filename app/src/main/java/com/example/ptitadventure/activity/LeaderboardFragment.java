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
import com.example.ptitadventure.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardFragment extends Fragment {

    /*private CircleImageView imageFirstPlace, imageSecondPlace, imageThirdPlace;
    private TextView textFirstPlaceName, textSecondPlaceName, textThirdPlaceName;
    private TextView textFirstPlaceScore, textSecondPlaceScore, textThirdPlaceScore;
    private RecyclerView recyclerViewLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;
    private List<GameProgress> leaderboardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        initViews(view);
        setupRecyclerView();
        loadLeaderboardData();

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

    private void setupRecyclerView() {
        leaderboardList = new ArrayList<>();
        leaderboardList.add(new GameProgress(new Student("khanh", "123", "Nguyen Lam Kien", "t@gmail.com"
        , "0123456789", "1", "123", "1", 1, 123, 123, 123), null, 800, 1));
        leaderboardAdapter = new LeaderboardAdapter(leaderboardList);
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLeaderboard.setAdapter(leaderboardAdapter);
    }

    private void loadLeaderboardData() {
        // In a real app, you would fetch this data from a database or API
        List<GameProgress> allProgress = getDummyGameProgressList();

        // Sort the list by total score in descending order
        Collections.sort(allProgress, (gp1, gp2) -> Integer.compare(gp2.getTotalScore(), gp1.getTotalScore()));

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

    private void updateTopPlayer(TextView nameView, TextView scoreView, CircleImageView imageView, GameProgress progress) {
        nameView.setText(progress.getStudent().getFullName());
        scoreView.setText(progress.getTotalScore() + " pts");
        // In a real app, you would load the student's avatar here
        // imageView.setImageResource(R.drawable.default_avatar);
    }

    private List<GameProgress> getDummyGameProgressList() {
        List<GameProgress> progressList = new ArrayList<>();
        progressList.add(new GameProgress(new Student("khanh", "123", "Bui Duy Khanh", "khanh@e.com", "0123456789", "1",
                "123", "1", 1, 123, 123, 123), null, 1000, 1));
        return progressList;
    }

    private static class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

        private List<GameProgress> leaderboardList;

        public LeaderboardAdapter(List<GameProgress> leaderboardList) {
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
            GameProgress progress = leaderboardList.get(position);
            holder.bind(progress, position + 4); // +4 because top 3 are displayed separately
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

            public void bind(GameProgress progress, int rank) {
                textUserName.setText(progress.getStudent().getFullName());
                textUserLevel.setText("Cấp độ: " + calculateLevel(progress.getTotalScore()));
                textUserPoints.setText(progress.getTotalScore() + " điểm");
                textUserRank.setText("#" + rank);
                // In a real app, you would load the student's avatar here
                // imageUserAvatar.setImageResource(R.drawable.default_avatar);
            }

            private int calculateLevel(int totalScore) {
                // This is a simple level calculation. Adjust as needed.
                return (totalScore / 100) + 1;
            }
        }
    }*/
}