package com.example.ptitadventure.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ptitadventure.R;
import com.example.ptitadventure.api.ApiStudentQuestService;
import com.example.ptitadventure.api.Client;
import com.example.ptitadventure.dao.StudentQuestRepo;
import com.example.ptitadventure.model.StudentQuest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentStatisticsFragment extends Fragment {

    private TextView meanTextView;
    private BarChart barChart;

    private StudentQuestRepo studentQuestRepo;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_student_statistics, container, false);

        meanTextView = view.findViewById(R.id.text_mean_points);
        barChart = view.findViewById(R.id.bar_chart);

        // Use StudentQuestRepo (like the leaderboard)
        studentQuestRepo = new StudentQuestRepo(Client.getClient().create(ApiStudentQuestService.class));

        fetchStudentPoints();
        return view;
    }

    private void fetchStudentPoints() {
        studentQuestRepo.getDataLeaderboard(new Callback<List<StudentQuest>>() {
            @Override
            public void onResponse(Call<List<StudentQuest>> call, Response<List<StudentQuest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StudentQuest> questList = response.body();

                    // 1) Calculate the mean, including zeros in the sum and count
                    float sumAll = 0f;
                    int totalStudents = questList.size();
                    for (StudentQuest sq : questList) {
                        sumAll += sq.getScore();  // includes 0
                    }
                    float mean = (totalStudents == 0) ? 0f : (sumAll / totalStudents);

                    // 2) Filter out zero-score students for the chart (we won't draw them)
                    List<Float> nonZeroScores = new ArrayList<>();
                    for (StudentQuest sq : questList) {
                        if (sq.getScore() > 0) {
                            nonZeroScores.add((float)sq.getScore());
                        }
                    }

                    // 3) Update the mean text
                    meanTextView.setText("Điểm trung bình: " + String.format("%.2f", mean));

                    // 4) Populate chart with non-zero bars + one bar for mean in the middle
                    populateChart(nonZeroScores, mean);

                } else {
                    Log.e("StudentStatistics", "Error fetching quest list or empty response");
                }
            }

            @Override
            public void onFailure(Call<List<StudentQuest>> call, Throwable t) {
                Log.e("StudentStatistics", "Failure: " + t.getMessage());
            }
        });
    }

    /**
     * We have:
     *   - A list of non-zero student scores.
     *   - A float mean (calculated from *all* students, including zeros).
     * We place the mean bar in the middle of the non-zero bars.
     */
    private void populateChart(List<Float> nonZeroScores, float mean) {
        // 1) Prepare the Student Entries
        List<BarEntry> studentEntries = new ArrayList<>();
        for (int i = 0; i < nonZeroScores.size(); i++) {
            float score = nonZeroScores.get(i);
            studentEntries.add(new BarEntry(i, score));
        }

        // Create a BarDataSet for the student bars
        BarDataSet studentDataSet = new BarDataSet(studentEntries, "Điểm Sinh Viên");
        studentDataSet.setColor(getResources().getColor(R.color.not_completed_color));
        studentDataSet.setValueTextSize(12f);

        // 2) Mean bar
        // If we have N non-zero bars, place the mean bar at x = (N-1)/2
        //  - If N=0 (all scores=0), we put x=0 to show the single mean bar.
        float middleIndex = 0f;
        if (nonZeroScores.size() > 0) {
            middleIndex = (nonZeroScores.size() - 1) / 2f;
        }

        List<BarEntry> meanEntries = new ArrayList<>();
        meanEntries.add(new BarEntry(middleIndex, mean));

        BarDataSet meanDataSet = new BarDataSet(meanEntries, "Trung Bình");
        meanDataSet.setColor(Color.GREEN);
        meanDataSet.setValueTextSize(12f);

        // 3) Combine both data sets
        // If there are 0 non-zero bars, we only effectively show the mean bar
        BarData barData = new BarData(studentDataSet, meanDataSet);

        // 4) Assign data to chart
        barChart.setData(barData);

        // Hide X-axis labels (no student names)
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setEnabled(false);

        // Show bars from 0 upward
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMinimum(0f);

        // Hide right axis
        barChart.getAxisRight().setEnabled(false);

        // Custom description
        Description description = new Description();
        barChart.setDescription(description);

        // Refresh chart
        barChart.invalidate();
    }
}
