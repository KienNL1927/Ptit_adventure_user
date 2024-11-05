package com.example.ptitadventure.model;

public class Student extends User {
    private String studentId;
    private String majorCode;
    private String className;
    private int rank;
    private int score;
    private int completedQuests;
    private int trainingPoints;

    public Student(String username, String password, String fullName, String email, String phoneNumber, String studentId, String majorCode, String className, int rank, int score, int completedQuests, int trainingPoints) {
        super(username, password, fullName, email, phoneNumber);
        this.studentId = studentId;
        this.majorCode = majorCode;
        this.className = className;
        this.rank = rank;
        this.score = score;
        this.completedQuests = completedQuests;
        this.trainingPoints = trainingPoints;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCompletedQuests() {
        return completedQuests;
    }

    public void setCompletedQuests(int completedQuests) {
        this.completedQuests = completedQuests;
    }

    public int getTrainingPoints() {
        return trainingPoints;
    }

    public void setTrainingPoints(int trainingPoints) {
        this.trainingPoints = trainingPoints;
    }
}
