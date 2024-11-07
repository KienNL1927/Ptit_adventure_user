package com.example.ptitadventure.model;

import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean status;
    private int parent_id;
    private List<Quiz> quiz;
    private List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Quest(int id, String name, String description, boolean status, int parent_id, List<Quiz> quiz, List<Location> locations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.parent_id = parent_id;
        this.quiz = quiz;
        this.locations = locations;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public Quest(int id, String name, String description, boolean status, int parent_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
