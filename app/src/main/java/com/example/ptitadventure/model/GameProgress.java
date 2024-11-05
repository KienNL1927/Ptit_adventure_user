package com.example.ptitadventure.model;

import java.util.List;

public class GameProgress {
    private Student student;
    private List<CheckPoint> completedCheckpoints;
    private int totalScore;
    private int totalTrainingPoints;

    public GameProgress(Student student, List<CheckPoint> completedCheckpoints, int totalScore, int totalTrainingPoints) {
        this.student = student;
        this.completedCheckpoints = completedCheckpoints;
        this.totalScore = totalScore;
        this.totalTrainingPoints = totalTrainingPoints;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<CheckPoint> getCompletedCheckpoints() {
        return completedCheckpoints;
    }

    public void setCompletedCheckpoints(List<CheckPoint> completedCheckpoints) {
        this.completedCheckpoints = completedCheckpoints;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalTrainingPoints() {
        return totalTrainingPoints;
    }

    public void setTotalTrainingPoints(int totalTrainingPoints) {
        this.totalTrainingPoints = totalTrainingPoints;
    }
}
