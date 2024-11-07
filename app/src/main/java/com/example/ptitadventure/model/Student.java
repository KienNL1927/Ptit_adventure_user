package com.example.ptitadventure.model;

public class Student extends User {
    private String studentId;
    private String majorCode;
    private String className;

    public Student(String username, String password, String fullName, String email, String phoneNumber, String studentId, String majorCode, String className) {
        super(username, password, fullName, email, phoneNumber);
        this.studentId = studentId;
        this.majorCode = majorCode;
        this.className = className;
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
}
