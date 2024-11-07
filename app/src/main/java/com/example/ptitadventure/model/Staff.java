package com.example.ptitadventure.model;

public class Staff extends User {
    private String teacherId;
    private String faculty;
    private String staffCode;

    public Staff(String username, String password, String fullName, String email, String phoneNumber, String teacherId, String faculty, String staffCode) {
        super(username, password, fullName, email, phoneNumber);
        this.teacherId = teacherId;
        this.faculty = faculty;
        this.staffCode = staffCode;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
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
