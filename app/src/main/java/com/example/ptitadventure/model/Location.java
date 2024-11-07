package com.example.ptitadventure.model;

public class Location {

    private int id;
    private String name;
    private int floors;
    private String building;


    public Location(int id, String name, int floors, String building) {
        this.id = id;
        this.name = name;
        this.floors = floors;
        this.building = building;
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

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}