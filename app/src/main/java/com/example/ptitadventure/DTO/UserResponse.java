package com.example.ptitadventure.DTO;

public class UserResponse {
    private String role;
    private String student_id;
    private String staff_id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public UserResponse(String role, String student_id, String staff_id) {
        this.role = role;
        this.student_id = student_id;
        this.staff_id = staff_id;
    }
}
