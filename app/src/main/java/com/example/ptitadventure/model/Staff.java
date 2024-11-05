package com.example.ptitadventure.model;

public class Staff extends User {
    private String teacherId;
    private String faculty;

    public Staff(String username, String password, String fullName, String email, String phoneNumber, String teacherId, String faculty) {
        super(username, password, fullName, email, phoneNumber);
        this.teacherId = teacherId;
        this.faculty = faculty;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
