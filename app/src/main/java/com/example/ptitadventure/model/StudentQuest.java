package com.example.ptitadventure.model;

import java.util.List;

public class StudentQuest {

    private int id;

    private int score;

    private List<Student> students;

    private List<Quest> quests;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public StudentQuest(int id, int score, List<Student> students, List<Quest> quests) {
        this.id = id;
        this.score = score;
        this.students = students;
        this.quests = quests;
    }
}
