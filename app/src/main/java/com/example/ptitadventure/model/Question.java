package com.example.ptitadventure.model;

import java.util.List;
public class Question {
    private String id;
    private String text;
    private int point;
    private List<Choice> choices;

    public Question(String id, String text, int point, List<Choice> choices) {
        this.id = id;
        this.text = text;
        this.point = point;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
