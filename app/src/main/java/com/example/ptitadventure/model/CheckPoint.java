package com.example.ptitadventure.model;

import java.util.List;

public class CheckPoint {
    private String id;
    private String name;
    private String description;
    private int buildings;
    private List<Subtask> subtasks;

    public int getBuildings() {
        return buildings;
    }

    public void setBuildings(int buildings) {
        this.buildings = buildings;
    }

    private Quiz quiz;
    private int trainingPoints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getTrainingPoints() {
        return trainingPoints;
    }

    public void setTrainingPoints(int trainingPoints) {
        this.trainingPoints = trainingPoints;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public CheckPoint(String id, String name, String description, int buildings, List<Subtask> subtasks, Quiz quiz, int trainingPoints, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.buildings = buildings;
        this.subtasks = subtasks;
        this.quiz = quiz;
        this.trainingPoints = trainingPoints;
        this.completed = completed;
    }

    public int getCompletedSubtasks() {
        int count = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    public int getTotalSubtasks() {
        return subtasks.size();
    }
    private boolean completed;
}
