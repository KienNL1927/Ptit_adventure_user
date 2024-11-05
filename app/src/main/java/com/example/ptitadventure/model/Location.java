package com.example.ptitadventure.model;

public class Location {
    private String name;
    private int floors;
    private int completedSubtasks;
    private int totalSubtasks;

    public Location(String name, int floors, int totalSubtasks) {
        this.name = name;
        this.floors = floors;
        this.totalSubtasks = totalSubtasks;
        this.completedSubtasks = 0;
    }

    // Getters and setters

    public void completeSubtask() {
        if (completedSubtasks < totalSubtasks) {
            completedSubtasks++;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getCompletedSubtasks() {
        return completedSubtasks;
    }

    public void setCompletedSubtasks(int completedSubtasks) {
        this.completedSubtasks = completedSubtasks;
    }

    public int getTotalSubtasks() {
        return totalSubtasks;
    }

    public void setTotalSubtasks(int totalSubtasks) {
        this.totalSubtasks = totalSubtasks;
    }

    public boolean isCompleted() {
        return completedSubtasks == totalSubtasks;
    }
}