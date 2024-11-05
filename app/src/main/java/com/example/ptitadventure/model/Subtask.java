package com.example.ptitadventure.model;

public class Subtask {
    private String id;
    private String description;
    private boolean completed;

    private int floors;

    public Subtask(String id, String description, boolean completed, int floors) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.floors = floors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
