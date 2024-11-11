package com.example.ptitadventure.model;

import java.util.List;

public class StudentQuest {

    private int id;

    private int score;

    private Student students;

    private Quest quests;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

    public Quest getQuests() {
        return quests;
    }

    public void setQuests(Quest quests) {
        this.quests = quests;
    }

    public StudentQuest(int id, int score, Student students, Quest quests) {
        this.id = id;
        this.score = score;
        this.students = students;
        this.quests = quests;
    }
}
