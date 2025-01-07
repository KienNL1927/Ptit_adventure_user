package com.example.ptitadventure.DTO;

public class AllStudentDTO {
    private String name;
    private int id;
    private double points;
    public AllStudentDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPoints() {
        return points;
    }
}
